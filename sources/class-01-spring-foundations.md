# Class 01 — Spring Framework, Spring Boot & REST APIs
(Sourced from Notion workspace: "Spring Boot API Lab" / "Class 01 — Spring Framework, Spring Boot & REST APIs")

## Lecture focus
Understand what Spring Boot gives us, how Spring manages application objects, and how APIs expose resources over HTTP.

## 1. The Spring ecosystem
- Spring Framework: Core application framework (the foundation)
- Spring Web: Build web applications and HTTP APIs (web/API layer)
- Spring Security: Authentication and authorization
- Spring Cloud: Patterns for distributed systems
- Spring Boot: Fast, opinionated setup for Spring

## 2. Spring vs Spring Boot
Spring Framework is the underlying toolkit.
Spring Boot is built on Spring:
- Auto-configuration
- Starter dependencies
- Embedded server
- Sensible defaults

> Spring is the toolbox. Spring Boot is the ready-to-run workbench.

## 3. Spring internals: the container, IoC, and DI
- Without Spring: `new ProductService()` tightly couples classes together.
- Inversion of Control (IoC): The Spring container owns object creation and lifecycle.
- Dependency Injection (DI): Dependencies supplied from outside, preferably constructor injection (`final` fields).
- Flow: `HTTP request → Controller → Service → Repository → Database`

## 4. Configuration management
`application.properties`, `application-dev.properties`, `application-prod.properties` (profiles).

## 5. APIs and the API contract
- HTTP method + endpoint
- Request params/body
- Response body and data shape
- HTTP status codes & errors

## 6. REST API best practices
1. Model resources (`/products`, `/products/{id}`)
2. Correct HTTP methods (GET, POST, PUT, PATCH, DELETE)
3. Consistent JSON
4. Statelessness
5. Query parameters for filtering/pagination
