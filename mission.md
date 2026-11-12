# Current Mission: Step 4 — Making Data Permanent (Databases & Spring Data JPA)

**Active Mission:** Connecting a Database so Data Survives Server Restarts  
**Learning Style:** The Feynman Technique  
**Goal:** Right now, when we stop the server, our tasks in `List<Task>` disappear. We will connect an **in-memory H2 SQL database** and use **Spring Data JPA** so tasks are saved in real database tables with zero SQL queries written by hand!

---
### Checklist
- [ ] Understand the "Notebook vs Whiteboard" analogy (Why we need databases)
- [ ] Add `spring-boot-starter-data-jpa` and `h2` database to `pom.xml`
- [ ] Turn `Task` into a database table using `@Entity` and `@Id`
- [ ] Create `TaskRepository extends JpaRepository<Task, Long>` (Spring's automatic database helper)
- [ ] Update `TaskController` to use the repository instead of the `ArrayList`
- [ ] Test that data is saved and inspect the H2 web console!
