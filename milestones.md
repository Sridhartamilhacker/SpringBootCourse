# 🏆 Milestones & Conceptual Breakthroughs

*Achievements unlocked by building and debugging real code.*

### 🗄️ Milestone 12: The Database Architect — Flyway Migrations & Containerized PostgreSQL
- **Unlocked:** 2026-11-18
- **What You Accomplished:**
  - Designed production-grade DDL schema with audit trails, optimistic locking, and financial precision (`NUMERIC(19,4)`).
  - Authored versioned SQL migration scripts `V1__create_wallet_and_ledger_tables.sql` and `V2__seed_system_treasury.sql`.
  - Diagnosed Spring Boot modular auto-configuration mechanics and configured `spring-boot-starter-flyway`.
  - Applied migrations live against a containerized PostgreSQL 16 database with `ddl-auto: validate`.
  - Enforced zero-loss double-entry accounting constraints directly at the database engine level.

### 🩺 Milestone 11: The Production Pilot — Spring Boot Actuator Mastered!
- **Unlocked:** 2026-11-16
- **What You Accomplished:**
  - Added `spring-boot-starter-actuator` and activated production telemetry with zero Java code.
  - Inspected real-time `/actuator/health` revealing database (`H2`), disk space, and Kubernetes liveness/readiness probes.
  - Monitored live JVM heap memory consumption via `/actuator/metrics/jvm.memory.used`.
  - Tracked HTTP server request volume and latency profiles via `/actuator/metrics/http.server.requests`.
  - Mastered production security guidelines: exposing public health probes while shielding internal operational metrics.

### ☕ Milestone 10: The Master of Beans — Lifecycle Hooks & Scopes Mastered!
- **Unlocked:** 2026-11-16
- **What You Accomplished:**
  - Deconstructed Bean Scopes using the "Office Coffee Machine" (Singleton) vs "Paper Cup" (Prototype) analogy.
  - Implemented `@PostConstruct` and `@PreDestroy` lifecycle hooks and watched them execute live in terminal logs.
  - Proved the Singleton design pattern live using `System.identityHashCode()` across concurrent web requests.
  - Mastered the Golden Rule of Spring Singletons: why service beans must remain strictly stateless.

### 🛡️ Milestone 9: The Safety Net — Database Transactions & Proxies Mastered!
- **Unlocked:** 2026-11-15
- **What You Accomplished:**
  - Mastered the Feynman "Bank Transfer" and All-or-Nothing ACID transaction contract.
  - Implemented `@Transactional` on `TaskService` and exposed comparison endpoints in `TaskController`.
  - Discovered and dissected Spring's runtime "stunt double" — the CGLIB Dynamic AOP Proxy (`TaskService$$SpringCGLIB$$0`).
  - Observed `TransactionInterceptor` and `TransactionAspectSupport` orchestrating `BEGIN TRANSACTION` and `ROLLBACK`.
  - Proved live via H2 that unprotected crashes leave corrupted records while `@Transactional` completely undoes changes on error.

### 🔀 Milestone 8: The Traffic Controller — DispatcherServlet Mastered!
- **Unlocked:** 2026-11-14
- **What You Accomplished:**
  - Grasped the Front Controller architectural pattern in Spring MVC.
  - Traced the complete lifecycle of an HTTP request through `DispatcherServlet`.
  - Discovered `RequestMappingHandlerMapping` (the URL lookup directory).
  - Learned how `HandlerAdapter` binds arguments (`@PathVariable`, `@RequestBody`) and invokes methods.
  - Observed Jackson `HttpMessageConverter` serialize and deserialize JSON live in terminal DEBUG logs.

### 🔬 Milestone 7: The Mechanic — Auto-Configuration Internals Mastered!
- **Unlocked:** 2026-11-14
- **What You Accomplished:**
  - Deconstructed `@SpringBootApplication` into its 3 core components: `@SpringBootConfiguration`, `@ComponentScan`, and `@EnableAutoConfiguration`.
  - Mastered the conditional evaluation engine (`@ConditionalOnClass`, `@ConditionalOnMissingBean`, `@ConditionalOnProperty`).
  - Enabled and interpreted the live `CONDITIONS EVALUATION REPORT` with `debug=true`.
  - Understood the difference between Positive Matches (activated subsystems) and Negative Matches (skipped libraries).
  - Grasped Spring's "Convention over Configuration" philosophy: sensible defaults that gracefully yield to custom user beans.

### 🛡️ Milestone 6: The Guardian — Input Validation & Global Error Handling Mastered!
- **Unlocked:** 2026-11-13
- **What You Accomplished:**
  - Added `spring-boot-starter-validation` to `pom.xml`.
  - Enforced business rules with `@NotBlank` on domain models.
  - Placed the `@Valid` bouncer at controller entry points (`@RequestBody`).
  - Created a centralized `@RestControllerAdvice` Customer Service Desk.
  - Eliminated messy 50-line internal Java stack traces from client responses.
  - Delivered clean, production-grade JSON error maps: `{"title": "Title must not be empty or blank"}` with `400 Bad Request`.

### 🚦 Milestone 5: The Diplomat — HTTP Status Codes & REST Headers Mastered!
- **Unlocked:** 2026-11-13
- **What You Accomplished:**
  - Upgraded raw controller return types to `ResponseEntity<T>`.
  - Replaced misleading `200 OK` on missing IDs with standard `404 Not Found`.
  - Implemented gold-standard REST `201 Created` with dynamic `Location` headers via `ServletUriComponentsBuilder`.
  - Mastered `204 No Content` for clean, payload-free deletions.
  - Verified end-to-end HTTP response status codes and headers live with `curl -i`.

### 🗄️ Milestone 4: The Data Architect — Spring Data JPA & SQL Database Mastered!
- **Unlocked:** 2026-11-12
- **What You Accomplished:**
  - Connected an in-memory relational SQL database (H2) via `pom.xml`.
  - Configured `application.properties` to expose the H2 web console and show live SQL logs.
  - Decorated `Task.java` with JPA `@Entity`, `@Id`, and `@GeneratedValue`.
  - Created a 4-line `TaskRepository extends JpaRepository<Task, Long>`.
  - Replaced manual `ArrayList` with real database operations (`save`, `findAll`, `findById`, `deleteById`).
  - Observed live SQL queries (`INSERT`, `SELECT`, `UPDATE`, `DELETE`) generated by Hibernate in terminal logs.

### 📋 Milestone 3: The Builder — Full CRUD REST API with JSON Mastered!
- **Unlocked:** 2026-11-12
- **What You Accomplished:**
  - Created a brand-new Spring Boot project using IntelliJ IDEA.
  - Built a domain model `Task.java` with POJO conventions (getters, setters, constructors).
  - Wrote a full `@RestController` supporting all 4 HTTP verbs: `GET`, `POST`, `PUT`, `DELETE`.
  - Mastered `@RequestBody` for incoming JSON translation.
  - Verified end-to-end CRUD lifecycle: Reading tasks, adding task #3, updating task #2, and deleting task #1.

### ✉️ Milestone 2: The Conversationalist — Passing Data via URLs
- **Unlocked:** 2026-11-11
- **What You Accomplished:**
  - Learned the difference between Query Parameters (`@RequestParam`) and Path Variables (`@PathVariable`).
  - Added dynamic routes to your live Spring Boot app.
  - Implemented safe default values (`defaultValue = "Friend"`).
  - Understood Spring's automatic string-to-int type conversion.

### 🚀 Milestone 1: The Ignition — First Web Server & Live API Running!
- **Unlocked:** 2026-11-11
- **What You Accomplished:**
  - Configured a clean Maven build using `spring-boot-starter-web`.
  - Diagnosed and fixed a Maven XML tag placement error.
  - Wrote your very first `@SpringBootApplication` and `@RestController` class by hand.
  - Successfully booted an embedded Apache Tomcat web server on port 8080.
  - Verified live HTTP response: `"Hello, my first spring boot application!"`.
