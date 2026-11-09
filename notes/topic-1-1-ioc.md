# Topic 1.1: Why Spring Exists & Inversion of Control (IoC)

> **Architect's Perspective:** Spring is not a web framework. Spring is an object factory, dependency graph coordinator, and lifecycle manager. The web and data tiers are merely plugins running on top of this core engine.

---

## 1. Core Concept & The Design Rationale

### The Pain That Forced Its Creation
Before Spring, enterprise Java applications instantiated dependencies using the `new` keyword or heavy EJB Service Locators:
```java
// Anti-pattern: Hard-coded tight coupling
public class OrderService {
    private PaymentGateway paymentGateway = new StripePaymentGateway();
    private OrderRepository repository = new PostgresOrderRepository();
}
```
**Why this fails in production:**
1. **Untestable Code:** You cannot test `OrderService` without charging a real credit card or connecting to a live PostgreSQL instance.
2. **Cascading Constructor Refactoring:** If `PostgresOrderRepository` changes its constructor parameters to require a connection pool or metrics registry, every single class calling `new PostgresOrderRepository()` across the entire codebase breaks.
3. **Hidden State & Lifecycle Chaos:** Who closes the database connections? Who disposes the thread pools? When classes create their own dependencies, there is no centralized lifecycle management.

### The Mental Model: The Assembly Line
Inversion of Control (IoC) moves object instantiation and wiring out of the caller and into a dedicated environment (the Container).
- A class declares its required dependencies as `private final` constructor parameters.
- The IoC Container instantiates the dependencies first, resolves the dependency graph, and injects them into the dependent class.

---

## 2. Under the Hood (Runtime Internals)

### What the Container Actually Is
At runtime, Spring's `BeanFactory` is fundamentally a concurrent hash map of bean definitions and instantiated singletons, combined with reflection (`java.lang.reflect`).

### Dependency Resolution Algorithm (`DefaultListableBeanFactory`)
When Spring constructs a bean (e.g. `OrderService`), it calls `ConstructorResolver`:
1. **Introspection:** Spring inspects constructor parameters via reflection.
2. **Candidate Search (`findAutowireCandidates`):** It queries its internal registry for all beans assignable to the parameter type (e.g., `PaymentGateway.class`).
3. **Single Match:** If exactly one bean matches, Spring supplies it.
4. **Multiple Matches (Ambiguity Resolution):**
   - **`@Primary` Check:** Spring checks if one candidate is annotated with `@Primary` (`beanDefinition.isPrimary()`). If yes, that candidate wins.
   - **`@Priority` Check:** Spring checks for `jakarta.annotation.Priority`.
   - **Parameter Name Fallback:** Spring attempts to match the parameter identifier (e.g. `stripePaymentGateway`) to registered bean IDs.
   - **Failure:** If none of these resolve the conflict, it throws `NoUniqueBeanDefinitionException`.
5. **Zero Matches:** It throws `NoSuchBeanDefinitionException`.

---

## 3. Common Production Failure Modes

### 1. `NoUniqueBeanDefinitionException`
- **Symptom:** Application fails on startup: `expected single matching bean but found 2: payPalPaymentGateway, stripePaymentGateway`.
- **Root Cause:** Multiple beans implement the requested interface, and the injection target did not specify `@Primary` or `@Qualifier`.
- **Fix:** Mark the default bean with `@Primary`, or use `@Qualifier("beanName")` at the injection point.

### 2. The Zombie Bean (`NullPointerException` on `@Autowired` Fields)
- **Symptom:** `orderService.placeOrder()` throws `NullPointerException` on `paymentGateway`.
- **Root Cause:** A developer manually instantiated `OrderService` with `new OrderService()`, bypassing the Spring container entirely.
- **Fix:** Always obtain Spring beans from the container or inject them. Never mix `new` with Spring-managed services.

### 3. Circular Dependency (`BeanCurrentlyInCreationException`)
- **Symptom:** Bean A requires Bean B, which requires Bean A.
- **Root Cause:** Tightly coupled cyclic graph during constructor resolution.
- **Fix:** Redesign the architecture (extract shared logic to a separate Service C or use domain events).

---

## 4. Production Interview Questions with Worked Answers

### Q1: Why is Constructor Injection preferred over Field Injection (`@Autowired` on private fields)?
**Worked Answer:**
> "Field injection seems convenient because it requires less boilerplate, but it introduces three serious architectural flaws:
> 1. **Untestable outside the container:** With field injection, you cannot instantiate the class in a pure JUnit test without using reflection or bringing up the Spring context. With constructor injection, dependencies are passed plainly via `new MyService(mockRepo)`.
> 2. **Immutability:** Constructor injection allows dependencies to be marked `final`, ensuring the bean is fully initialized and immutable once constructed, eliminating thread-safety and partial-state bugs.
> 3. **Hidden code smells:** If a class has 8 dependencies, constructor injection makes the smell immediately obvious (an 8-argument constructor). Field injection hides bloated responsibilities because adding another `@Autowired` field looks harmless."

### Q2: What is the difference between `@Primary` and `@Qualifier`? When would you use each?
**Worked Answer:**
> "`@Primary` defines a system-wide default candidate when multiple beans implement the same interface. It is set at the bean definition level.
> `@Qualifier` provides fine-grained targeting at the injection site, specifying the exact bean ID to inject.
> **Trade-off:** Use `@Primary` when 90% of your application uses a standard implementation (e.g., `StripePaymentGateway`), but you have a specialized one for certain flows. Use `@Qualifier` when multiple implementations coexist with equal weight (e.g., `DomesticTaxCalculator` vs `InternationalTaxCalculator`) and callers must explicitly choose which one they need."

### Q3: What is the difference between `BeanFactory` and `ApplicationContext`?
**Worked Answer:**
> "`BeanFactory` is the root interface providing basic IoC configuration and lazy bean loading. 
> `ApplicationContext` extends `BeanFactory` and is the enterprise container used in production. It adds eager singleton instantiation on startup (catching wiring errors at boot time rather than runtime), internationalization (i18n), event propagation (`ApplicationEvent`), and seamless integration with Spring AOP and environment profiles."

---

## 5. Connections to Next Topics
- **Topic 1.2:** We will dissect the `ApplicationContext` hierarchy, lifecycle events, and inspect beans directly in memory.
- **Topic 1.3:** We will dive into `@Component`, `@Service`, and `@Repository` semantics and how Spring scans the classpath.
