# Learning Records

### Session — 2026-11-11: Practical Playground Launch (Feynman Technique)
- **Learner Request:** Pivot to hands-on practical learning first; build web APIs directly; Feynman technique with simple analogies; user writes all code. Cleaned up old exercises and reset Notion.
- **Milestone 1 Completed:** Step 1 — Hello Spring Boot: Your First Web API.
- **Hands-On Work:**
  - User created project structure and `pom.xml`.
  - Diagnosed `<devToolsPlugin>` malformed XML error.
  - User wrote `HelloApplication.java` with `@SpringBootApplication`, `@RestController`, `@GetMapping("/hello")`.
  - Diagnosed missing `public` keyword on `main`.
  - Application launched successfully on port 8080.
  - Verified live via `curl -s http://localhost:8080/hello`.
- **Artifacts Generated:**
  - Local Note: `notes/01-hello-spring-boot.md`
  - Notion Note: Published to `https://app.notion.com/p/4153bf92ecc88136b600e794d326b45d?pvs=204` under "🌱 Spring Boot API Lab"
### Session 2 — 2026-11-11: Passing Data (Query Params & Path Variables)
- **Milestone 2 Completed:** Step 2 — Passing Data via `@RequestParam` & `@PathVariable`.
- **Hands-On Work:**
  - Added `@RequestParam(defaultValue = "Friend") String name` for `/greet`.
  - Added `@PathVariable int id` for `/orders/{id}`.
  - Tested dynamic parameters and automatic string-to-int conversion.
- **Artifacts Generated:**
  - Local Note: `notes/02-passing-data.md`
  - Notion Note: Published to `https://app.notion.com/p/4153bf92ecc881fdb09cdd12181095e9?pvs=204` under "🌱 Spring Boot API Lab"
### Session 3 — 2026-11-12: Full CRUD REST API with JSON in IntelliJ IDEA
- **Milestone 3 Completed:** Step 3 — Building a Real In-Memory CRUD API (Task Manager).
- **Hands-On Work:**
  - User created `02-task-manager-api` using IntelliJ IDEA's Maven project creation.
  - User wrote `Task.java` (POJO model) and `TaskManagerApplication.java` (`@SpringBootApplication`).
  - User wrote `TaskController.java` with `@RestController`, `@RequestMapping("/tasks")`.
  - Implemented all 4 CRUD actions: `GET` all, `GET` by ID, `POST` with `@RequestBody`, `PUT` with `@PathVariable` and `@RequestBody`, and `DELETE`.
  - Verified live via curl:
    - Initial `GET /tasks`: 2 items returned as JSON array.
    - `POST /tasks`: Added item #3 with JSON body.
    - `PUT /tasks/2`: Updated item #2 `completed` status to `true`.
    - `DELETE /tasks/1`: Deleted item #1.
    - Final `GET /tasks`: Confirmed state persistence in memory across requests.
- **Artifacts Generated:**
  - Local Note: `notes/03-crud-api.md`
  - Notion Note: Published to `https://app.notion.com/p/4163bf92ecc881549b4fc5299ce7d2e8?pvs=204` under "🌱 Spring Boot API Lab"
### Session 4 — 2026-11-12: Spring Data JPA & H2 SQL Database
- **Milestone 4 Completed:** Step 4 — Making Data Permanent (Spring Data JPA & H2 Database).
- **Hands-On Work:**
  - Added `spring-boot-starter-data-jpa` and `h2` database dependencies.
  - Configured `application.properties` (`spring.h2.console.enabled=true`, `spring.jpa.show-sql=true`).
  - Added `@Entity`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)` to `Task.java`.
  - Created `TaskRepository extends JpaRepository<Task, Long>`.
  - Refactored `TaskController` to inject and use `TaskRepository` for persistence.
  - Verified live SQL lifecycle:
    - Empty initial table: `GET /tasks` -> `[]`.
    - Inserted 2 tasks via `POST`: Hibernate generated `insert into task...`.
    - Retrieved all: `GET /tasks` -> returned tasks with auto-incremented IDs 1 and 2.
    - Updated task 1: `PUT /tasks/1` -> Hibernate executed `update task...`.
    - Deleted task 2: `DELETE /tasks/2` -> Hibernate executed `delete from task...`.
- **Artifacts Generated:**
  - Local Note: `notes/04-spring-data-jpa.md`
  - Notion Note: Published to `https://app.notion.com/p/4163bf92ecc8815884d0ef4ef445495f?pvs=204` under "🌱 Spring Boot API Lab"
- **Next Mission:** Step 5: Friendly Error Messages & Status Codes (`ResponseEntity`, 404 Not Found, 201 Created).
