# Learning Records

### Session — 2026-11-11: Practical Playground Launch (Feynman Technique)
- **Learner Request:** Pivot to hands-on practical learning first; build web APIs directly; Feynman technique with simple analogies; user writes all code. Cleaned up old exercises and reset Notion.
- **Milestone 1 Completed:** Step 1 — Hello Spring Boot: Your First Web API.
- **Hands-On Work:**
  - User created project structure and `pom.xml`.
  - Diagnosed `<devToolsPlugin>` malformed XML error.
  - User wrote `HelloApplication.java` with `@SpringBootApplication`, `@RestController`, `@GetMapping("/hello")`.
  - Diagnosed missing `public` keyword on `main`.
  - Application launched successfully on port 8080.
  - Verified live via `curl -s http://localhost:8080/hello`.
- **Artifacts Generated:**
  - Local Note: `notes/01-hello-spring-boot.md`
  - Notion Note: Published to `https://app.notion.com/p/4153bf92ecc88136b600e794d326b45d?pvs=204` under "🌱 Spring Boot API Lab"
- **Next Mission:** Step 2: Passing Data — How the Web Talks to Your Code (`@RequestParam` & `@PathVariable`).
