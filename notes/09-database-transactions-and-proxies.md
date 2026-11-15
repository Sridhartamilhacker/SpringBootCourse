# Deep Dive 03: Behind the Scenes of Database Transactions (`@Transactional`)

> **The Feynman Analogy:**  
> - **The Bank Transfer Disaster:** Imagine moving $100 from Account A to Account B.
>   1. Deduct $100 from Account A.
>   2. 💥 *(Server crashes or power goes out!)*
>   3. Add $100 to Account B.
>   - Without a transaction, Account A lost $100, and Account B never got it. The money evaporated.
> - **The All-or-Nothing Contract:** A transaction is an unbreakable safety container: either **every single step succeeds** (`COMMIT`), or if anything fails, the system hits an automatic undo button so it looks like **nothing ever happened** (`ROLLBACK`).

---

## 1. What Just Happened?

We built two contrasting test methods in `TaskService` and proved the behavior live against the H2 database:

| Test Route | Annotation | What Happened On Crash | Database Result |
| :--- | :--- | :--- | :--- |
| **`/tasks/test-unprotected`** | None | Raw crash without rollback | `"Unprotected Task"` **was permanently written** to DB! (Corrupted state) |
| **`/tasks/test-protected`** | `@Transactional` | Spring caught the error & executed `ROLLBACK` | `"Protected Task"` **never existed** in the DB! (Safe) |

---

## 2. Deconstructing the Live Stack Trace: The "Stunt Double"

When we hit `/tasks/test-protected`, the error stack trace revealed the exact internal plumbing Spring uses:

```text
com.example.taskmanager.TaskService$$SpringCGLIB$$0.createTaskWithTransaction(<generated>)
  at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(...)
  at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(...)
  at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(...)
  at com.example.taskmanager.TaskService.createTaskWithTransaction(TaskService.java:21)
```

### The 4 Internal Steps:
1. **`TaskService$$SpringCGLIB$$0` (The Stunt Double):**
   - Spring does not inject your raw `TaskService` into `TaskController`.
   - Instead, Spring subclasses your class at runtime using **CGLIB** to create a proxy ("stunt double").
2. **`TransactionInterceptor` & `TransactionAspectSupport`:**
   - The proxy intercepts the call before it reaches your real Java code.
   - It tells the `PlatformTransactionManager`: **`BEGIN TRANSACTION`**.
3. **Your Real Code Runs:**
   - `taskRepository.save(new Task("Protected Task", false))` is executed.
4. **The Rollback Trigger:**
   - When `throw new RuntimeException(...)` occurs, the exception escapes back into the proxy.
   - The proxy catches it, issues **`ROLLBACK`** to H2, and only then re-throws the exception up to the controller.

---

## 3. Visual Execution Pipeline

```text
TaskController
     │
     ▼ (calls createTaskWithTransaction)
┌────────────────────────────────────────────────────────┐
│ Spring CGLIB Proxy (TaskService$$SpringCGLIB$$0)       │
│                                                        │
│  1. Open DB Connection & BEGIN TRANSACTION             │
│  2. try {                                              │
│         target.createTaskWithTransaction(); // REAL    │
│         COMMIT;                                        │
│     } catch (RuntimeException ex) {                    │
│         ROLLBACK; // ◄── Reverts all SQL changes!      │
│         throw ex;                                      │
│     } finally {                                        │
│         Close / Return connection to pool              │
│     }                                                  │
└────────────────────────────────────────────────────────┘
```

---

## 4. Crucial Spring Gotchas to Remember

1. **Self-Invocation Bypasses Transactions:**
   - If `methodA()` inside `TaskService` calls `this.methodB()` (which has `@Transactional`), **the transaction will NOT start**!
   - Why? Because `this` calls the raw Java object, bypassing the CGLIB stunt double!
2. **Default Rollback Rule:**
   - By default, Spring only rolls back for **Unchecked Exceptions** (`RuntimeException` and `Error`).
   - If your method throws a checked `Exception`, Spring commits by default unless you specify:
     `@Transactional(rollbackFor = Exception.class)`
