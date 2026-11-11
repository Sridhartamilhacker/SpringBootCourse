# Step 01: Hello Spring Boot — Your Very First Web API

> **The Feynman Analogy:**  
> A Web API is simply a **restaurant waiter**.  
> - You (the customer / browser) sit at a table and ask: *"Please bring me `/hello`."* (HTTP Request)  
> - The waiter (`@RestController`) runs to the kitchen, gets the message `"Hello, my first spring boot application!"`, and serves it back to you on a plate (HTTP Response).

---

## 1. What Just Happened?

With just **one single Java file** and a `pom.xml`, you started a complete, enterprise-grade web server running on your computer!

When you ran `mvn spring-boot:run`:
1. Spring Boot downloaded and launched an **embedded web server (Apache Tomcat)** inside your JVM.
2. It started listening on **port 8080** for incoming web traffic.
3. When you visit `http://localhost:8080/hello`, Spring routes the request to your `sayHello()` method.
4. Your Java method returns the text, and Spring delivers it straight to the browser or terminal.

---

## 2. Deconstructing the Code (Line by Line)

Here is the exact code you wrote in `HelloApplication.java`:

```java
package com.example.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 1. The Ignition Key: Boots up Spring Boot and the embedded Tomcat server
@SpringBootApplication

// 2. The Waiter: Tells Spring "this class handles web requests"
@RestController
public class HelloApplication {

    // 3. The Door: Standard Java entry point
    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    // 4. The Menu Item: When someone requests GET /hello, run this method!
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, my first spring boot application!";
    }
}
```

### The 3 Magical Annotations Explained Simply:

| Annotation | Plain English Meaning |
| :--- | :--- |
| **`@SpringBootApplication`** | **The Ignition Key.** Tells Java: *"This is a Spring Boot app! Start the engine, find all our components, and start the embedded web server."* |
| **`@RestController`** | **The Waiter.** Tells Spring: *"This class answers customers from the web, and whatever my methods return should go directly back as web data."* |
| **`@GetMapping("/hello")`** | **The Bell.** Tells Spring: *"Whenever an HTTP GET request arrives at `/hello`, ring this bell and execute the method underneath."* |

---

## 3. The Two Bugs We Diagnosed and Fixed

### Bug 1: Unknown Tag in `pom.xml`
- **What happened:** Added `<devToolsPlugin>` directly inside `<project>`.
- **The Error:** `Malformed POM: Unrecognised tag: 'devToolsPlugin'`.
- **Why:** In Maven, all libraries belong in the `<dependencies>` shopping cart as a `<dependency>`.
- **The Lesson:** Keep all third-party libraries neatly inside `<dependencies>...</dependencies>`.

### Bug 2: Missing `public` on `main`
- **What happened:** `static void main(String[] args)`.
- **Why:** The JVM requires the main method to be `public` so external launch tools (like Maven's spring-boot plugin) have permission to enter and start the application.

---

## 4. Quick Recall Table

| Concept | What It Does |
| :--- | :--- |
| **Port 8080** | The default "door number" on your computer where Spring Boot listens for web requests. |
| **HTTP GET** | The web method used to "ask for" or "read" data (like opening a webpage). |
| **`spring-boot-starter-web`** | The "Combo Meal" dependency that includes Tomcat, routing, and JSON tools all in one. |
