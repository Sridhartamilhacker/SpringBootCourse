# Topic 1.3: Bean Definition, Injection Styles & Stereotype Annotations

> **Quick Mental Model:**  
> Think of Spring annotations like roles in a hospital. Everyone on staff is an employee (`@Component`), but a doctor (`@Service`) diagnoses illness, a nurse/lab technician (`@Repository`) handles blood draws and physical records, and the front desk receptionist (`@Controller`) greets patients. They are all beans in the container, but `@Repository` gets special equipment: a protective shield (proxy) that translates dangerous vendor-specific database crashes into clean, safe medical charts (`DataAccessException`).

---

## 1. The Story: Why Did We Create Three Stereotypes Instead of Just `@Component`?

When Spring 1.0 was released, developers only had `@Component`. They slapped `@Component` on every single class:

```java
@Component
public class CustomerDao { }     // Database access

@Component
public class CustomerService { } // Business logic

@Component
public class CustomerApi { }     // Web endpoints
```

It worked, but two painful problems surfaced in production:

### Problem A: Architectural Blindness (No Semantic Signals)
When reading a codebase with 500 classes, if every class is marked with `@Component`, you cannot tell what a class does without reading all of its methods. 
By introducing **Stereotypes**, we create clear boundaries:
- `@Service`: Expresses business logic, calculations, and orchestrations.
- `@Repository`: Expresses persistence, database queries, and storage operations.
- `@Controller` / `@RestController`: Expresses HTTP request handling, routing, and JSON serialization.

### Problem B: Vendor-Specific Database Crashes Leaking into Business Code
If you talk to an Oracle database and a table is missing, JDBC throws:
`java.sql.SQLException: ORA-00942: table or view does not exist`.

If you talk to PostgreSQL, it throws a completely different error code. If your business code tries to catch raw `SQLException`, your business layer becomes permanently locked to one database vendor!

---

## 2. Deconstructing the Magic: What Are Stereotypes Underneath?

Look at the actual source code of `@Service` inside Spring Framework:

```java
// Spring Framework source code:
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // <-- LOOK HERE! @Service is literally @Component with a name badge!
public @interface Service {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
```

Spring uses **meta-annotations**. Because `@Component` is stamped on `@Service` and `@Repository`, Spring's classpath scanner treats all of them as candidate beans.

### The Secret Superpower of `@Repository`

While `@Service` is just a semantic badge, **`@Repository` triggers real runtime behavior!**

Spring automatically registers a `BeanPostProcessor` called `PersistenceExceptionTranslationPostProcessor`. 
- When Spring sees `@Repository` on a class, it **wraps that class in a proxy**.
- If a method in that repository throws a raw vendor exception (`SQLException`, `HibernateException`), the proxy intercepts it.
- It translates that vendor exception into a Spring unified **`DataAccessException`** (such as `DataIntegrityViolationException` or `CannotAcquireLockException`).

```
Database Error (ORA-00942 or PG-42P01)
                 │
                 ▼
     [@Repository Dynamic Proxy] ── catches raw vendor exception
                 │
                 ▼
  Translates into Spring DataAccessException
                 │
                 ▼
     Clean, vendor-agnostic error handed to @Service
```

Your business service never knows or cares what database vendor threw the error.

---

## 3. The Great Injection Debate: Constructor vs. Field vs. Setter

### Style 1: Field Injection (The Dangerous "Shortcut")
```java
@Service
public class OrderService {
    @Autowired
    private PaymentGateway paymentGateway; // Injected via reflection
}
```
**Why senior engineers reject Field Injection:**
1. **Impossible to Unit Test Cleanly:** You cannot test `OrderService` with plain `new OrderService()`. The fields remain `null` because they are `private` with no constructor or setters. You are forced to boot Spring or use messy reflection tricks.
2. **Cannot Be `final`:** Fields cannot be immutable, meaning they could theoretically be mutated or left uninitialized.
3. **Hides Bloated Responsibilities:** A class can have 12 `@Autowired` fields and still look tidy in an editor, disguising the fact that the class violates the Single Responsibility Principle.

### Style 2: Setter Injection (Rarely Used)
```java
@Service
public class OrderService {
    private PaymentGateway paymentGateway;

    @Autowired
    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
}
```
Useful only for truly **optional** dependencies that have sensible defaults if omitted.

### Style 3: Constructor Injection (The Production Gold Standard)
```java
@Service
public class OrderService {
    private final PaymentGateway paymentGateway;
    private final OrderRepository orderRepository;

    // In Spring 4.3+, @Autowired is OPTIONAL if there is only one constructor!
    public OrderService(PaymentGateway paymentGateway, OrderRepository orderRepository) {
        this.paymentGateway = paymentGateway;
        this.orderRepository = orderRepository;
    }
}
```
**Why Constructor Injection Wins:**
- **100% Immutable:** Fields are marked `final`.
- **Pure Unit Testable:** You can write `new OrderService(mockGateway, mockRepo)` in plain JUnit in 1 millisecond.
- **Fail-Fast:** The object cannot be constructed in a half-baked or `null` state.

---

## 4. The Live Exercise We Built

In [`exercises/topic-1-3-injection-styles/`](file:///home/sridhar/Java_Learning/SpringBootCourse/exercises/topic-1-3-injection-styles/), we extended our Order Processing system:

- **`OrderIdGenerator` ([`@Component`](file:///home/sridhar/Java_Learning/SpringBootCourse/exercises/topic-1-3-injection-styles/src/main/java/com/mastery/ioc/util/OrderIdGenerator.java)):** A pure utility class generating prefixed IDs like `ORD-5a3690`.
- **`InMemoryOrderRepository` ([`@Repository`](file:///home/sridhar/Java_Learning/SpringBootCourse/exercises/topic-1-3-injection-styles/src/main/java/com/mastery/ioc/repository/InMemoryOrderRepository.java)):** Simulates database persistence and demonstrates exception translation when saving invalid null orders.
- **`PricingService` ([`@Service`](file:///home/sridhar/Java_Learning/SpringBootCourse/exercises/topic-1-3-injection-styles/src/main/java/com/mastery/ioc/service/PricingService.java)):** Pure business logic calculating discounts.
- **`OrderService` ([`@Service`](file:///home/sridhar/Java_Learning/SpringBootCourse/exercises/topic-1-3-injection-styles/src/main/java/com/mastery/ioc/service/OrderService.java)):** Orchestrator using clean constructor injection with zero `@Autowired` boilerplate.

---

## 5. The Production Incident: The Circular Dependency Death Lock 💀

### The Bug Scenario
A teammate added a discount feature. They wired `OrderService` into `PricingService`, while `OrderService` already required `PricingService`:

```
OrderService ─── needs ───▶ PricingService
     ▲                           │
     │                           │
     └───────── needs ───────────┘
```

When running the application, Spring crashed with:

```text
Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: 
Error creating bean with name 'orderService': 
Requested bean is currently in creation: Is there an unresolvable circular reference?
```

### The Diagnosis & The Fix
- **Why it happens:** Spring starts creating `OrderService` $\rightarrow$ realizes it needs `PricingService` $\rightarrow$ pauses and tries to create `PricingService` $\rightarrow$ realizes it needs `OrderService` $\rightarrow$ tries to create `OrderService` again.
- **The Tracking Set:** Inside `DefaultSingletonBeanRegistry`, Spring checks `singletonsCurrentlyInCreation.add("orderService")`. Because `"orderService"` is already in the set, Spring catches the infinite loop and throws `BeanCurrentlyInCreationException`.
- **The Fix:** We looked at `PricingService` and asked: *"Does calculating a discount actually need `OrderService`?"* No! `PricingService` just calculates numbers. We removed the unnecessary dependency, broke the cycle, and the build immediately succeeded.

---

## 6. Real-World Interview Q&A

### Q1: What is the difference between `@Component`, `@Service`, and `@Repository`?
**Answer:**
> "`@Component` is the generic root stereotype for any Spring-managed bean.  
> `@Service` is a specialization of `@Component` that designates classes containing business logic. It has the same runtime behavior as `@Component`, but provides clear architectural readability.  
> `@Repository` is a specialization for the persistence layer. In addition to registering the bean, it enables automatic **Persistence Exception Translation**, wrapping the class in a proxy that catches low-level vendor-specific database exceptions (like JDBC `SQLException`) and rethrows them as Spring's unified `DataAccessException` hierarchy."

### Q2: Why does Spring throw `BeanCurrentlyInCreationException`? How do you fix circular dependencies?
**Answer:**
> "It occurs when two or more beans depend on each other in a cycle during constructor injection (Bean A requires Bean B, which requires Bean A). Since constructor parameters must exist before an object can be instantiated, the JVM cannot instantiate either.  
> **How to fix it:**
> 1. **Redesign (Best Practice):** Circular dependency is a design smell indicating tight coupling. Extract the shared logic into a third service (Service C) that both A and B can depend on, or eliminate the unneeded dependency.
> 2. **Domain Events:** Use `ApplicationEventPublisher` so Service A publishes an event and Service B listens to it asynchronously, decoupling the two.
> 3. **`@Lazy` (Last Resort):** Annotating one constructor parameter with `@Lazy` instructs Spring to inject a synthetic proxy instead of the real bean, breaking the startup cycle."

---

## 7. Memory Anchors (Quick Recall Table)

| Annotation / Concept | Plain English Translation |
| :--- | :--- |
| **`@Component`** | "I am a generic Spring bean. Register me in the container." |
| **`@Service`** | "I hold business logic. Register me, and document my layer." |
| **`@Repository`** | "I talk to storage. Register me, and translate all raw database exceptions into Spring exceptions." |
| **Constructor Injection** | Pass dependencies through the constructor. Guarantees immutability (`final`) and fast unit testing. |
| **`BeanCurrentlyInCreationException`** | Circular dependency deadlock: Bean A needs Bean B to construct, and Bean B needs Bean A. |
| **Single Constructor Rule** | In Spring 4.3+, if a class has only one constructor, `@Autowired` is 100% optional. |
