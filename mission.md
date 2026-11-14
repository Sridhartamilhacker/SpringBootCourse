# Current Mission: Part 2 — Deep Dive 2: The Heart of Spring Web (`DispatcherServlet`)!

**Active Mission:** The Traffic Controller of the Web (`DispatcherServlet`, `HandlerMapping`, `HandlerAdapter`)  
**Learning Style:** The Feynman Technique  

### Goal:
When you run `curl http://localhost:8080/tasks/1`, how does Apache Tomcat know to route that specific URL to `public ResponseEntity<Task> getTaskById(@PathVariable Long id)` in `TaskController.java`?
We will learn:
1. **The "Grand Central Station" Analogy:** The DispatcherServlet as the chief station master.
2. **The 3 Key Actors:**
   - `DispatcherServlet` (The Front Controller)
   - `HandlerMapping` (The GPS Map: which URL points to which method)
   - `HandlerAdapter` (The Universal Plug: invokes your method and passes the arguments)
   - `HttpMessageConverter` (The Translator: Jackson turning JSON to Java and back)
3. **Trace a Live Request:** Step through the execution path from HTTP socket to Java return value.

---
### Checklist
- [ ] Understand the Grand Central Station analogy
- [ ] Trace the path of an incoming HTTP request through `DispatcherServlet`
- [ ] Understand `HandlerMapping` and `HandlerAdapter`
- [ ] See how `HttpMessageConverter` (Jackson) translates JSON behind the scenes
