# Step 05: Friendly Error Messages & HTTP Status Codes (`ResponseEntity`)

> **The Feynman Analogy:**  
> - **Default Spring Controllers:** A waiter who always smiles, says *"Everything went great!"* (HTTP 200 OK), and sometimes hands you an empty plate (`null`).
> - **Professional Status Codes (`ResponseEntity`):** A **traffic light** combined with a **postal envelope**:
>   - The **Envelope (`ResponseEntity<T>`)** holds your letter (the JSON body), but also lets you pick the **stamp** (HTTP Status Code) and the **return tracking slip** (`Location` header).
>   - **Green Light (2xx):** `200 OK` (Here is your data), `201 Created` (New item saved!), `204 No Content` (Deleted successfully!).
>   - **Yellow/Orange Light (4xx):** `404 Not Found` (That item does not exist!).
>   - **Red Light (5xx):** `500 Internal Server Error` (Server crash/exception).

---

## 1. What Just Happened?

We transformed our API from returning misleading `200 OK with null` into an enterprise-grade RESTful API communicating with standard HTTP status codes and headers:

| Request | Old Response | New Professional Response | Why? |
| :--- | :--- | :--- | :--- |
| `GET /tasks/999` | `200 OK` + `null` | **`404 Not Found`** | Missing resource explicitly reported |
| `POST /tasks` | `200 OK` + JSON | **`201 Created`** + `Location` header | New record born, tracking URL provided |
| `GET /tasks/1` | `200 OK` + JSON | **`200 OK`** + JSON | Found and returned |
| `PUT /tasks/1` | `200 OK` + JSON | **`200 OK`** + JSON | Updated and returned |
| `PUT /tasks/999` | `200 OK` + `null` | **`404 Not Found`** | Cannot update non-existent resource |
| `DELETE /tasks/1` | `200 OK` + String | **`204 No Content`** | Deleted cleanly; no body payload needed |
| `DELETE /tasks/1` (repeat) | (would error/no-op) | **`404 Not Found`** | Cannot delete non-existent task |

---

## 2. Deconstructing the Code

### 1. The Postal Envelope: `ResponseEntity<T>`
Normally, Spring wraps any returned Java object into a default `200 OK` envelope. `ResponseEntity` gives us manual control:

```java
// Returning 200 OK with body
return ResponseEntity.ok(task);

// Returning 404 Not Found with empty body
return ResponseEntity.notFound().build();
```

### 2. The Gold Standard for Resource Creation: `Location` Header
When returning `201 Created`, RFC standards recommend returning a `Location` header pointing to the new resource URL. Spring makes this simple with `ServletUriComponentsBuilder`:

```java
@PostMapping
public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
    Task savedTask = taskRepository.save(newTask);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()             // "http://localhost:8080/tasks"
            .path("/{id}")                    // append "/{id}"
            .buildAndExpand(savedTask.getId())// replace with actual database ID
            .toUri();

    return ResponseEntity.created(location).body(savedTask);
}
```

### 3. Clean Delete Semantics: `204 No Content`
When deleting, returning data is redundant. HTTP `204 No Content` signifies success without an unnecessary payload:

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    if (!taskRepository.existsById(id)) {
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
    taskRepository.deleteById(id);
    return ResponseEntity.noContent().build();   // 204 No Content
}
```

---

## 3. Live Verification Proof (Terminal Logs via `curl -i`)

```http
/* POST /tasks */
HTTP/1.1 201 
Location: http://localhost:8080/tasks/1
Content-Type: application/json
{"completed":false,"id":1,"title":"Learn HTTP Status Codes"}

/* GET /tasks/999 (Non-existent ID) */
HTTP/1.1 404 
Content-Length: 0

/* DELETE /tasks/1 */
HTTP/1.1 204 

/* DELETE /tasks/1 (Second time) */
HTTP/1.1 404 
Content-Length: 0
```

---

## 4. HTTP Status Code Quick Reference

| Status Code | Name | Meaning | Common Usage |
| :--- | :--- | :--- | :--- |
| **`200`** | **OK** | Request succeeded. | `GET`, `PUT` returning data. |
| **`201`** | **Created** | Resource was successfully created. | `POST` creating new entities. |
| **`204`** | **No Content** | Success, but nothing to return. | `DELETE` operations. |
| **`400`** | **Bad Request** | Client sent malformed / invalid data. | Failed validation checks. |
| **`404`** | **Not Found** | Resource at given URI does not exist. | Invalid ID lookups. |
| **`500`** | **Internal Server Error** | Server crashed / uncaught exception. | Unhandled bugs or DB outages. |
