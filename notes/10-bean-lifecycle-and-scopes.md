# Deep Dive 04: Bean Lifecycle, Scopes, and Identity

> **The Feynman Analogy:**  
> - **The Office Coffee Machine (Singleton Scope):** The building only buys **one** machine. Whether 1 person or 1,000 employees want coffee, they all share that same machine.
> - **The Paper Cup (Prototype Scope):** Every time someone approaches the machine, they grab a brand-new disposable cup.
> - **The Lifecycle of a Bean:**
>   1. **Birth:** Constructor called (`new TaskService()`).
>   2. **Equipping:** Spring injects all dependencies (`TaskRepository`).
>   3. **Orientation:** `@PostConstruct` method runs setup code (caches, startup checks).
>   4. **Career:** Handles live HTTP requests.
>   5. **Retirement:** Server shuts down, `@PreDestroy` method runs cleanup (close sockets, flush buffers).

---

## 1. What Just Happened?

We proved two fundamental Spring Boot architectural truths:

### 1. Lifecycle Hooks Live
In `TaskService.java`:
```java
@PostConstruct
public void init() {
    System.out.println("👉 [2. @PostConstruct] TaskService dependencies injected! Ready for action.");
}

@PreDestroy
public void cleanup() {
    System.out.println("👉 [3. @PreDestroy] Application shutting down! TaskService cleaning up.");
}
```
- The constructor ran first.
- `@PostConstruct` executed immediately after Spring wired the dependencies.
- `@PreDestroy` executes gracefully when the JVM shuts down.

### 2. The Singleton Proof
We created `GET /tasks/scope-check` returning `System.identityHashCode(taskService)`.  
Calling the endpoint repeatedly returned the **exact same memory hash**:
```text
TaskService instance memory ID: 1548293021
TaskService instance memory ID: 1548293021
TaskService instance memory ID: 1548293021
```
This confirms that Spring creates **one single instance** at startup and reuses it across every concurrent web request.

---

## 2. The Golden Rule of Spring Singletons

Because Singletons are shared across all incoming web requests (threads):

> ⚠️ **NEVER store mutable user-specific state in class fields of a `@Service` or `@RestController`!**

```java
// ❌ DANGEROUS! Shared across all users!
@Service
public class TaskService {
    private Long currentUserId; // User A's ID will overwrite User B's ID!
}

// ✅ SAFE & STATELESS!
@Service
public class TaskService {
    public void processTask(Long userId) { // Lives safely on the thread's local stack!
    }
}
```

---

## 3. Bean Scopes Comparison

| Scope | Instances Created | Lifespan | When to Use |
| :--- | :--- | :--- | :--- |
| **`singleton`** *(Default)* | Exactly **1** per Spring Container | Entire application lifetime | Stateless services, repositories, controllers (99% of use cases). |
| **`prototype`** | **New instance** on every injection/call | Until garbage collected | Stateful objects, temporary worker beans. |
| **`request`** | **1** per HTTP request | Single web request | Web-specific state tracking (rare). |
| **`session`** | **1** per HTTP session | User login session | Shopping carts, user preferences (rare). |
