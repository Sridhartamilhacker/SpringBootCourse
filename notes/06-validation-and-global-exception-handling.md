# Step 06: Input Validation & Global Error Handling (`@Valid` & `@RestControllerAdvice`)

> **The Feynman Analogy:**  
> - **Without Validation:** A nightclub with no bouncer. Anyone can walk in barefoot or in pajamas, walk up to the bartender (the database), and get served broken records (`{"title": ""}`).
> - **The Bouncer (`@Valid` + `@NotBlank`):** Checks the dress code right at the front door (`@RequestBody`). If rules are violated, they are turned away with `400 Bad Request` before reaching your controller logic or database.
> - **The Customer Service Desk (`@RestControllerAdvice`):** Instead of handing the customer an ugly 50-page internal incident report (the default stack trace), the front desk formats a polite, concise card: `{"title": "Title must not be empty or blank"}`.

---

## 1. What Just Happened?

We secured our REST API with automated validation and centralized exception handling:

1. Added `spring-boot-starter-validation` to `pom.xml`.
2. Annotated the `title` field in `Task.java` with `@NotBlank(message = "Title must not be empty or blank")`.
3. Placed `@Valid` before `@RequestBody` in `TaskController` (`POST` and `PUT`).
4. Created `GlobalExceptionHandler` with `@RestControllerAdvice` and `@ExceptionHandler(MethodArgumentNotValidException.class)`.
5. Replaced a 50-line messy internal stack trace with a clean, field-by-field JSON error map.

---

## 2. Code Breakdown

### 1. The Model Dress Code: `Task.java`
```java
import jakarta.validation.constraints.NotBlank;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title must not be empty or blank")
    private String title;

    private boolean completed;
    ...
}
```

### 2. The Controller Bouncer: `TaskController.java`
```java
@PostMapping
public ResponseEntity<Task> createTask(@Valid @RequestBody Task newTask) {
    ...
}

@PutMapping("/{id}")
public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task updated) {
    ...
}
```

### 3. The Centralized Customer Service: `GlobalExceptionHandler.java`
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
```

---

## 3. Before vs After Proof (Terminal Logs)

### ❌ Before: The 50-Page Stack Trace Manual
```json
{
  "timestamp": "2026-11-13T14:30:01.065Z",
  "status": 400,
  "error": "Bad Request",
  "trace": "org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public ... \n\tat org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor...",
  "message": "Validation failed for object='task'. Error count: 1",
  "errors": [...]
}
```

### ✅ After: Clean, Client-Friendly Error JSON
```http
HTTP/1.1 400 
Content-Type: application/json

{
  "title": "Title must not be empty or blank"
}
```

---

## 4. Key Takeaways Table

| Annotation | Purpose | Analogy |
| :--- | :--- | :--- |
| **`@NotBlank`** | Rejects `null`, empty string `""`, or whitespace only `"   "`. | The dress code requirement. |
| **`@Valid`** | Instructs Spring MVC to execute validation on the incoming payload. | The bouncer checking ID and dress code at the door. |
| **`@RestControllerAdvice`** | Intercepts exceptions across all `@RestController` classes globally. | Centralized customer service desk for the entire building. |
| **`@ExceptionHandler`** | Specifies the exact exception type to intercept (e.g. `MethodArgumentNotValidException`). | The direct phone hotline for that specific category of issue. |
