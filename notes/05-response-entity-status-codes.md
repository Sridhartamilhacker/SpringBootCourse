# Step 05: Friendly Error Messages & Status Codes (`ResponseEntity`)

> **The Feynman Analogy:**  
> - **Raw Object Return:** Handing someone a raw piece of paper with no envelope.  
> - **`ResponseEntity<T>`:** Putting that paper inside an **official postal envelope**:  
>   - **Outside the envelope:** Stamped with a **Traffic Light Status Code** (e.g. `201 CREATED`, `404 NOT FOUND`).  
>   - **Inside the envelope:** The actual **Body** data (the `Task` or error details).

---

## 1. What Just Happened?

You upgraded your API from returning plain objects to returning **standard HTTP Status Codes** using `ResponseEntity`:

| HTTP Code | Name | In Spring Code | When We Use It |
| :--- | :--- | :--- | :--- |
| **`200`** | `OK` | `ResponseEntity.ok(task)` | Successfully read or updated a resource. |
| **`201`** | `Created` | `ResponseEntity.status(HttpStatus.CREATED).body(task)` | A brand new row was inserted via `POST`. |
| **`204`** | `No Content`| `ResponseEntity.noContent().build()` | Successfully deleted a resource via `DELETE`. |
| **`404`** | `Not Found` | `ResponseEntity.notFound().build()` | The requested ID does not exist in the database. |

---

## 2. The Code We Wrote

```java
// READ ONE: 200 OK if found, 404 NOT FOUND if missing
@GetMapping("/{id}")
public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    return taskRepository.findById(id)
            .map(task -> ResponseEntity.ok(task))
            .orElse(ResponseEntity.notFound().build());
}

// CREATE: 201 CREATED
@PostMapping
public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
    Task savedTask = taskRepository.save(newTask);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
}

// DELETE: 204 NO CONTENT if deleted, 404 NOT FOUND if ID doesn't exist
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    if (!taskRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    taskRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}
```

---

## 3. Quick Recall Table

| Concept | Plain English Meaning |
| :--- | :--- |
| **`ResponseEntity<T>`** | A wrapper that lets you control the HTTP status code, headers, and body returned to the client. |
| **`201 Created`** | Standard REST response when a `POST` creates a new item. |
| **`404 Not Found`** | Standard REST response when a requested resource ID does not exist. |
| **`204 No Content`** | Standard REST response when an item is deleted (success, but nothing left to return). |
