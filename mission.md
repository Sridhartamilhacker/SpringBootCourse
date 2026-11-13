# Current Mission: Part 2 — Deep Dive 1: How Auto-Configuration Works!

**Active Mission:** Peeking Behind the Magic Curtain (`@EnableAutoConfiguration` & Conditional Annotations)  
**Learning Style:** The Feynman Technique  

### Goal:
So far, you added `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2`, and `spring-boot-starter-validation`.
Magically:
- An Apache Tomcat web server started up.
- Jackson started converting JSON back and forth.
- Hibernate connected to an in-memory H2 database and created tables.
- Hibernate Validator started validating fields.

**How did all that happen with ZERO configuration files?**
In this deep dive, we will uncover:
1. **The "Smart Factory" Analogy:** How Spring Boot uses conditional assembly lines.
2. **`@EnableAutoConfiguration`:** What it does when `@SpringBootApplication` runs.
3. **The Sherlock Holmes of Spring:** Conditional annotations (`@ConditionalOnClass`, `@ConditionalOnMissingBean`, `@ConditionalOnProperty`).
4. **Seeing the Auto-Configuration Report Live:** Run the app with `--debug` and read Spring's internal decision log!

---
### Checklist
- [ ] Understand the "Smart Factory" analogy for Auto-Configuration
- [ ] Inspect `@SpringBootApplication` and see `@EnableAutoConfiguration` inside it
- [ ] Explore `@ConditionalOnClass` and `@ConditionalOnMissingBean`
- [ ] Run the app with `--debug` to read the live Auto-Configuration Report!
