# Step 03: Building a Real CRUD API (Task Manager)

> **The Feynman Analogy:**  
> Think of a refrigerator door with sticky notes:  
> - **C**reate (`POST`): You write a new note and stick it on the fridge.  
> - **R**ead (`GET`): You look at the fridge and read the notes.  
> - **U**pdate (`PUT`): You rewrite a note to check off a task as completed.  
> - **D**elete (`DELETE`): You crumple up a note and throw it in the trash.

---

## 1. What Just Happened?

You just built and launched a **complete, full-fledged RESTful CRUD API** in Java!

When a client sends JSON text across the internet:
```json
{
  "title": "Buy groceries",
  "completed": false
}
```
Spring Boot automatically:
1. Receives the raw HTTP JSON text.
2. Uses a built-in JSON converter (Jackson) to construct a real Java `Task` object.
3. Injects it into your `@RequestBody Task newTask` parameter.
4. When your method returns that `Task` or `List<Task>`, Spring automatically translates your Java objects back into clean JSON text for the client.

---

## 2. The 4 HTTP Verbs in Action

| HTTP Verb | Spring Annotation | URL Example | What It Does |
| :--- | :--- | :--- | :--- |
| **GET** | `@GetMapping` | `GET /tasks` | **Read All:** Retrieves the entire list of tasks. |
| **GET** | `@GetMapping("/{id}")` | `GET /tasks/1` | **Read One:** Retrieves a single task by its unique ID. |
| **POST** | `@PostMapping` | `POST /tasks` | **Create:** Accepts JSON in `@RequestBody` and saves a new task. |
| **PUT** | `@PutMapping("/{id}")` | `PUT /tasks/1` | **Update:** Modifies an existing task's fields. |
| **DELETE** | `@DeleteMapping("/{id}")`| `DELETE /tasks/1`| **Delete:** Removes the task from the list. |

---

## 3. The Code We Wrote

### 1. The Model: `Task.java`
A clean Plain Old Java Object (POJO) with:
- `Long id`
- `String title`
- `boolean completed`
- Default constructor + Getters/Setters (required so Spring can read/write fields during JSON translation).

### 2. The Controller: `TaskController.java`
```java
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    // READ ALL
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    // READ ONE
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // CREATE
    @PostMapping
    public Task createTask(@RequestBody Task newTask) {
        newTask.setId(nextId++);
        tasks.add(newTask);
        return newTask;
    }

    // UPDATE
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setTitle(updatedTask.getTitle());
                task.setCompleted(updatedTask.isCompleted());
                return task;
            }
        }
        return null;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
        return "Task #" + id + " has been deleted successfully!";
    }
}
```

---

## 4. Live Verification Output

Here are the actual live responses our API produced:

```bash
# 1. READ ALL:
GET http://localhost:8080/tasks
[{"id":1,"title":"Learn Spring Boot with Feynman","completed":true},{"id":2,"title":"Build my first CRUD Web API","completed":false}]

# 2. CREATE (POST JSON):
POST http://localhost:8080/tasks
Body: {"title": "Celebrate building a full CRUD API", "completed": false}
Response: {"id":3,"title":"Celebrate building a full CRUD API","completed":false}

# 3. UPDATE (PUT JSON):
PUT http://localhost:8080/tasks/2
Body: {"title": "Build my first CRUD Web API", "completed": true}
Response: {"id":2,"title":"Build my first CRUD Web API","completed":true}

# 4. DELETE:
DELETE http://localhost:8080/tasks/1
Response: Task #1 has been deleted successfully!
```

---

## 5. Quick Recall Table

| Concept | Plain English Meaning |
| :--- | :--- |
| **CRUD** | Create (`POST`), Read (`GET`), Update (`PUT`), Delete (`DELETE`). |
| **JSON** | Text format shaped like `{ "key": "value" }` used by all web browsers and mobile apps. |
| **`@RequestBody`** | Tells Spring: *"Take the JSON text sent in the request body and automatically convert it into my Java class object."* |
| **`@RequestMapping("/tasks")`** | Sets the common prefix path for all endpoints in the controller. |
| **Jackson** | The invisible library inside `spring-boot-starter-web` that translates Java $\leftrightarrow$ JSON. |
