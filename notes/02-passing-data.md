# Step 02: Passing Data — How the Web Talks to Your Code

> **The Feynman Analogy:**  
> Think of URLs like mailing a package:  
> - **Path Variable (`@PathVariable`):** The **house address** written on the box (`/orders/101`). It identifies the exact item.  
> - **Query Parameter (`@RequestParam`):** The **sticky note** on the box saying: *"Leave at front door?name=Sridhar"*. It provides extra options or filters.

---

## 1. The Two Ways to Pass Data in a URL

| Feature | Query Parameter (`@RequestParam`) | Path Variable (`@PathVariable`) |
| :--- | :--- | :--- |
| **URL Example** | `http://localhost:8080/greet?name=Sridhar` | `http://localhost:8080/orders/101` |
| **Indicator** | Uses a question mark `?` followed by `key=value` | Part of the path itself (`{id}`) |
| **Java Code** | `@RequestParam(defaultValue = "Friend") String name` | `@PathVariable int id` |
| **Best Used For** | Optional filters, sorting, search terms | Identifying a specific item/resource by ID |

---

## 2. The Code We Wrote

In `HelloApplication.java`:

```java
// 1. Query Parameter: Reads ?name=... from the URL
@GetMapping("/greet")
public String greet(@RequestParam(defaultValue = "Friend") String name) {
    return "Hello, " + name + "! Welcome to Spring Boot!";
}

// 2. Path Variable: Reads {id} from the URL path
@GetMapping("/orders/{id}")
public String getOrder(@PathVariable int id) {
    return "Fetching details for Order #" + id;
}
```

---

## 3. The "Hidden Magic" Spring Boot Did for You

### Automatic Type Conversion
Notice the method signature for orders:
```java
public String getOrder(@PathVariable int id)
```
When you typed `http://localhost:8080/orders/101` in your browser, the `101` in the address bar was just **plain text characters**. 

Spring automatically:
1. Extracted the text `"101"`.
2. Converted it into a real Java primitive **`int`**.
3. Handed it to your method!

**What if someone types `/orders/hello` (letters instead of numbers)?**  
Spring automatically detects that `"hello"` cannot be converted into an integer and sends back a polite **`400 Bad Request`** error without your application crashing!

### Default Values
With `@RequestParam(defaultValue = "Friend")`:
- If the user provides a name (`?name=Sridhar`), Spring uses `"Sridhar"`.
- If the user forgets the name (`/greet`), Spring automatically falls back to `"Friend"` so your code never gets `null`.

---

## 4. Quick Recall Table

| Concept | Plain English Meaning |
| :--- | :--- |
| **`@RequestParam`** | Reads key-value pairs after the `?` in the URL. |
| **`@PathVariable`** | Reads dynamic values between the slashes `/` in the URL path. |
| **`defaultValue`** | The fallback value to use if the user doesn't pass the parameter. |
| **Type Conversion** | Spring automatically turns URL text into Java types (`int`, `boolean`, `String`). |
