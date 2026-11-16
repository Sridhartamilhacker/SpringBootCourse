# Deep Dive 03: Behind the Scenes of Database Transactions (`@Transactional`)

> **The Feynman Analogy:**  
> - **The Master Blueprint & Tracing Paper:** An architect never draws directly on a multimillion-dollar blueprint with permanent ink. Instead, they lay down a sheet of **transparent tracing paper** (`BEGIN TRANSACTION`).
>   - If all modifications are completed cleanly: they stamp it onto the permanent parchment (**`COMMIT`**).
>   - If an ink bottle spills mid-way: they crumple up the tracing paper and throw it in the trash (**`ROLLBACK`**). The original blueprint is completely untouched!
> - **The Stunt Double (Dynamic Proxy):** You never call your service directly. Spring puts an invisible stunt double in front of your service. The stunt double opens the transaction, runs your method in a `try` block, and catches any `RuntimeException` to trigger a `rollback`.

---

## 1. The Core Problem: The Bank Transfer Disaster

Without transactions:
```java
accountRepo.deduct("Alice", 100);
// 💥 Power failure or database error here!
accountRepo.add("Bob", 100);
```
Alice loses $100, Bob receives $0. Money vanishes into thin air!

---

## 2. How Spring Fixes This with `@Transactional`

Spring eliminates the need for manual JDBC boilerplate:

```java
@Service
public class TaskService {
    @Transactional
    public void createTasks() {
        taskRepository.save(new Task("Task 1", false));
        // Any unhandled RuntimeException triggers an automatic ROLLBACK!
    }
}
```

---

## 3. How the Stunt Double (AOP Proxy) Works

```text
Caller (Controller)
       │
       ▼
[ Stunt Double / Proxy ]  (Generated in memory by Spring)
   ├── 1. Opens DB Connection (conn.setAutoCommit(false))
   │
   ├── 2. Calls YOUR real method:
   │      try {
   │          realService.createTasks();
   │          conn.commit();   // 3a. Success -> Make changes permanent!
   │      } catch (RuntimeException ex) {
   │          conn.rollback(); // 3b. Failure -> Revert all changes!
   │          throw ex;
   │      }
   │
   └── 3. Returns response
```

---

## 4. Key Takeaways

| Concept | Purpose |
| :--- | :--- |
| **`@Transactional`** | Guarantees atomic execution: either all database actions succeed, or none do. |
| **Commit** | Writes all pending SQL operations permanently to the database. |
| **Rollback** | Discards all pending SQL operations when a `RuntimeException` is thrown. |
| **Dynamic Proxy** | The invisible wrapper class that Spring creates to execute cross-cutting concerns (like transactions and security) before and after your method. |
