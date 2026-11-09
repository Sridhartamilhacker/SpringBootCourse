# Curriculum: Modern Spring Boot Mastery

> **Learner Level:** Beginner to Spring Boot (with basic Java foundation)  
> **Approach:** Practical, creator-led live engineering sessions using the Seven-Stage Mastery Loop (Confirm → Explain Why → Teach Concept → Build Exercise → Inject Bug → Go Deeper → Debrief).  
> **Authoritative References:**  
> - *Spring Boot Reference Documentation* (Spring.io)  
> - *Spring in Action, 6th Edition* by Craig Walls  
> - *Pro Spring 6* by Iuliana Cosmina, Rob Harrop, Chris Schaefer, Clarence Ho  
> - *Spring Boot in Practice* by Somnath Musib  
> - *Cloud Native Spring in Action* by Thomas Vitale  

---

## Phase 1: Core Spring & IoC Fundamentals (The Runtime Spine)
*Before Spring Boot's magic, you must understand the engine it runs on: the Spring Inversion of Control (IoC) Container.*

- [x] **Topic 1.1: Why Spring & Inversion of Control (IoC)**
  - *Focus:* Tight coupling vs. loose coupling, why `new` keyword kills testability and extensibility, Dependency Injection (DI) as a design pattern.
  - *Reference:* *Spring in Action*, Ch. 1; *Pro Spring 6*, Ch. 3.
- [ ] **Topic 1.2: The Spring ApplicationContext & BeanFactory**
  - *Focus:* What a "Bean" actually is in heap memory, `ApplicationContext` hierarchy, instantiating and inspecting beans programmatically.
  - *Reference:* *Spring Framework Core Documentation — The IoC Container*.
- [ ] **Topic 1.3: Bean Definition, Injection Styles & Stereotype Annotations**
  - *Focus:* `@Component`, `@Service`, `@Repository`, constructor injection vs. field injection (`@Autowired` pitfalls), circular dependency handling.
  - *Reference:* *Spring in Action*, Ch. 2; Effective Java Item 5.
- [ ] **Topic 1.4: Java Configuration & `@Bean` Methods**
  - *Focus:* `@Configuration` classes, CGLIB proxies, when to use `@Bean` vs `@Component` (third-party integration), method interception mechanics.
  - *Reference:* *Pro Spring 6*, Ch. 4.
- [ ] **Topic 1.5: Bean Lifecycle & Scopes**
  - *Focus:* Singleton vs Prototype scopes, thread-safety hazards, `@PostConstruct`, `@PreDestroy`, `InitializingBean`, `DisposableBean`, destruction gotchas.
  - *Reference:* *Spring Framework Core Documentation — Bean Scopes & Lifecycle*.

---

## Phase 2: Demystifying Spring Boot (Deconstructing the "Magic")
*Understanding how Spring Boot removes boilerplate without hiding how things work.*

- [ ] **Topic 2.1: The Anatomy of `@SpringBootApplication` & Startup Mechanism**
  - *Focus:* Deconstructing `@SpringBootConfiguration`, `@EnableAutoConfiguration`, `@ComponentScan`. What `SpringApplication.run()` actually executes.
  - *Reference:* *Spring Boot Reference Documentation — Using Spring Boot*.
- [ ] **Topic 2.2: Auto-Configuration Internals & Conditionals**
  - *Focus:* How auto-configuration works under the hood (`spring.factories` vs `AutoConfiguration.imports`), `@ConditionalOnClass`, `@ConditionalOnMissingBean`, `@ConditionalOnProperty`.
  - *Reference:* *Spring Boot in Practice*, Ch. 1 & 2.
- [ ] **Topic 2.3: Starter POMs & Dependency Management**
  - *Focus:* How `spring-boot-starter-*` coordinates transitive dependencies, Bill of Materials (BOM), avoiding dependency hell.
  - *Reference:* *Spring Boot Reference Documentation — Starters*.
- [ ] **Topic 2.4: Externalized Configuration & Type-Safe Properties**
  - *Focus:* `application.properties` vs `application.yml`, `@Value` vs `@ConfigurationProperties`, relaxed binding, validation with `@Validated`, profiles (`@Profile`).
  - *Reference:* *Spring Boot Reference Documentation — Externalized Configuration*.

---

## Phase 3: Modern Web APIs with Spring MVC
*Building robust, high-throughput RESTful services and mastering the HTTP pipeline.*

- [ ] **Topic 3.1: The Web Engine — DispatcherServlet & Request Flow**
  - *Focus:* The journey of an HTTP request: Connector -> Tomcat thread -> `DispatcherServlet` -> `HandlerMapping` -> `HandlerAdapter` -> Controller.
  - *Reference:* *Spring in Action*, Ch. 6; *Pro Spring 6*, Ch. 8.
- [ ] **Topic 3.2: Building REST Endpoints & Data Transfer Objects (DTOs)**
  - *Focus:* `@RestController`, `@GetMapping`, `@PostMapping`, `@RequestBody`, `@PathVariable`, `@RequestParam`, ResponseEntity, why domain entities should never escape to the API layer.
  - *Reference:* *Spring Boot in Practice*, Ch. 3.
- [ ] **Topic 3.3: Request Validation with Jakarta Bean Validation**
  - *Focus:* `@NotNull`, `@NotBlank`, `@Size`, `@Pattern`, `@Valid`, nested validation, custom constraint validators.
  - *Reference:* *Jakarta Bean Validation Specification*; *Spring in Action*, Ch. 3.
- [ ] **Topic 3.4: Production-Grade Error Handling & RFC 7807**
  - *Focus:* `@ControllerAdvice`, `@ExceptionHandler`, standard ProblemDetail responses, intercepting framework exceptions (MethodArgumentNotValidException, HttpMediaTypeNotSupportedException).
  - *Reference:* *Spring Boot Reference Documentation — Error Handling*.

---

## Phase 4: Data Persistence with Spring Data JPA & Hibernate
*Reliable, performant relational data access without boilerplate SQL leaks.*

- [ ] **Topic 4.1: Database Connection & DataSource Configuration**
  - *Focus:* HikariCP connection pool, dialect configuration, schema generation modes (`ddl-auto` risks in production).
  - *Reference:* *High-Performance Java Persistence* by Vlad Mihalcea.
- [ ] **Topic 4.2: JPA & Hibernate Fundamentals**
  - *Focus:* JPA specification vs Hibernate implementation, `@Entity`, `@Id`, `@GeneratedValue`, `EntityManager`, persistence context, first-level cache.
  - *Reference:* *Pro Spring 6*, Ch. 6; *High-Performance Java Persistence*.
- [ ] **Topic 4.3: Spring Data Repositories**
  - *Focus:* `JpaRepository`, query generation by method name, dynamic queries, custom queries with `@Query` (JPQL vs native SQL).
  - *Reference:* *Spring in Action*, Ch. 3; *Spring Data JPA Reference Documentation*.
- [ ] **Topic 4.4: Entity Relationships & The N+1 Query Problem**
  - *Focus:* `@OneToMany`, `@ManyToOne`, `@ManyToMany`, FetchType.LAZY vs EAGER, diagnosing and solving the N+1 problem with `JOIN FETCH` and `@EntityGraph`.
  - *Reference:* *High-Performance Java Persistence*, Ch. 12.
- [ ] **Topic 4.5: Transaction Management with `@Transactional`**
  - *Focus:* ACID properties, Spring AOP proxy mechanism around transactions, propagation types (`REQUIRED`, `REQUIRES_NEW`), rollback rules, self-invocation pitfalls.
  - *Reference:* *Pro Spring 6*, Ch. 7.

---

## Phase 5: Asynchronous Processing & Background Tasks
*Decoupling operations for resilient, non-blocking application architecture.*

- [ ] **Topic 5.1: Task Scheduling with `@Scheduled`**
  - *Focus:* Fixed rate vs fixed delay, cron expressions, scheduling thread pool tuning.
  - *Reference:* *Spring Boot in Practice*, Ch. 8.
- [ ] **Topic 5.2: Asynchronous Execution with `@Async`**
  - *Focus:* `@EnableAsync`, custom `ThreadPoolTaskExecutor`, CompletableFuture return types, thread pool starvation prevention.
  - *Reference:* *Spring Boot Reference Documentation — Task Execution and Scheduling*.

---

## Phase 6: Testing Strategy for Spring Applications
*Writing fast, reliable test suites across the testing pyramid.*

- [ ] **Topic 6.1: Unit Testing Spring Components with Mockito**
  - *Focus:* Testing without Spring context (pure fast unit tests), mocking dependencies, verifying behavior.
  - *Reference:* *Unit Testing Principles, Practices, and Patterns* by Vladimir Khorikov.
- [ ] **Topic 6.2: Web Layer Slice Testing with `@WebMvcTest`**
  - *Focus:* `MockMvc`, testing serialization, status codes, validations without bringing up full server or database.
  - *Reference:* *Spring in Action*, Ch. 16.
- [ ] **Topic 6.3: Data Layer Testing with `@DataJpaTest`**
  - *Focus:* In-memory testing, test slices, repository verification, transaction rollback in tests.
  - *Reference:* *Spring Boot Reference Documentation — Testing*.
- [ ] **Topic 6.4: Full Integration Testing with `@SpringBootTest` & Testcontainers**
  - *Focus:* Full application context lifecycle, testing against real PostgreSQL/MySQL containers, TestRestTemplate/WebTestClient.
  - *Reference:* *Cloud Native Spring in Action*, Ch. 4.

---

## Phase 7: Production Readiness & Observability
*Taking Spring Boot from localhost to enterprise production.*

- [ ] **Topic 7.1: Production Metrics with Spring Boot Actuator**
  - *Focus:* `/actuator/health`, `/actuator/info`, custom health indicators, security hardening for actuator endpoints.
  - *Reference:* *Spring Boot in Practice*, Ch. 10.
- [ ] **Topic 7.2: Structured Logging & Diagnostic Context**
  - *Focus:* SLF4J + Logback, log levels, MDC (Mapped Diagnostic Context) for request correlation IDs.
  - *Reference:* *Spring Boot Reference Documentation — Logging*.
- [ ] **Topic 7.3: Packaging, Native Executables & Containerization**
  - *Focus:* Executable JAR structure, layered JARs for Docker caching, graceful shutdown.
  - *Reference:* *Cloud Native Spring in Action*, Ch. 3.
