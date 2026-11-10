# 🌱 Spring Boot Mastery Laboratory

> A practical, code-first journey through Spring Framework and Spring Boot internals, built using the **Seven-Stage Mastery Engine**. Every topic is mastered by deconstructing the framework's magic, building real-world enterprise exercises, diagnosing injected production bugs, and debriefing like a senior backend engineer.

---

## 🧭 Learning Methodology: The Seven-Stage Mastery Loop

Every topic in this course passes through seven rigorous stages before the gate opens to the next concept:

1. **Confirm:** Calibrate mental models and define scope.
2. **Explain Why:** Deconstruct the real pain point that forced Spring's creators to build the feature.
3. **Teach Concept:** Live interactive session deconstructing the magic using raw vanilla Java first.
4. **Build Exercise:** Author an authentic, runnable, production-ready system adhering to SOLID principles.
5. **Inject Bug:** Introduce a realistic production failure mode (the primary learning instrument).
6. **Go Deeper:** Trace the runtime internals and actual framework source code.
7. **Debrief:** Explain root cause, diagnosis, and architectural trade-offs in an interview format.

---

## 🗺️ Curriculum & Progress

The full sequenced roadmap is tracked in [`curriculum.md`](curriculum.md).

### Phase 1: Core Spring & IoC Fundamentals (In Progress)
- [x] **Topic 1.1: Why Spring & Inversion of Control (IoC)**
  - *Code:* [`exercises/topic-1-1-ioc/`](exercises/topic-1-1-ioc/)
  - *Deep Notes:* [`notes/topic-1-1-ioc.md`](notes/topic-1-1-ioc.md) | [Notion](https://app.notion.com/p/4133bf92ecc8819da393da675e28be18?pvs=204)
  - *Mastery Unlocked:* The death of `new`, loose coupling, constructor injection, and resolving `NoUniqueBeanDefinitionException` via `@Primary` vs `@Qualifier`.
- [x] **Topic 1.2: The Spring ApplicationContext & BeanFactory**
  - *Code:* [`exercises/topic-1-2-application-context/`](exercises/topic-1-2-application-context/)
  - *Deep Notes:* [`notes/topic-1-2-application-context.md`](notes/topic-1-2-application-context.md) | [Notion](https://app.notion.com/p/4133bf92ecc881488a55e450b53c183c?pvs=204)
  - *Mastery Unlocked:* Eager pre-instantiation ("Fail-Fast"), two-phase startup (`BeanDefinition` vs singleton heap cache), container introspection, and ASM bytecode scanning.
- [x] **Topic 1.3: Bean Definition, Injection Styles & Stereotypes**
  - *Code:* [`exercises/topic-1-3-injection-styles/`](exercises/topic-1-3-injection-styles/)
  - *Deep Notes:* [`notes/topic-1-3-injection-styles.md`](notes/topic-1-3-injection-styles.md) | [Notion](https://app.notion.com/p/4143bf92ecc8810db8d8e2025d9adefb?pvs=204)
  - *Mastery Unlocked:* `@Component` vs `@Service` vs `@Repository`, `@Repository` dynamic proxy exception translation, field injection hazards, and breaking circular dependencies (`BeanCurrentlyInCreationException`).
- [ ] **Topic 1.4: Java Configuration & `@Bean` Methods** *(Next Mission)*
- [ ] **Topic 1.5: Bean Lifecycle & Scopes**

### Upcoming Phases
- **Phase 2:** Demystifying Spring Boot (Auto-Configuration, Starters, Actuator)
- **Phase 3:** Modern Web APIs with Spring MVC (REST, DTOs, Jakarta Validation, RFC 7807)
- **Phase 4:** Data Persistence with Spring Data JPA & Hibernate
- **Phase 5:** Asynchronous Processing & Task Scheduling
- **Phase 6:** Testing Strategy (MockMvc, `@DataJpaTest`, `@SpringBootTest`, Testcontainers)
- **Phase 7:** Production Readiness & Observability

---

## 📂 Repository Layout

```text
SpringBootCourse/
├── README.md               # Overview and progress dashboard
├── curriculum.md           # Master sequenced course roadmap
├── mission.md              # Current active mission and action checklist
├── milestones.md           # Unlocked achievement badges and breakthroughs
├── learning-records.md     # Detailed session logs with timestamps
├── sources/                # Knowledge base documents and class references
├── exercises/              # Hands-on, runnable Maven projects
│   ├── topic-1-1-ioc/
│   ├── topic-1-2-application-context/
│   └── topic-1-3-injection-styles/
├── notes/                  # Detailed, story-driven architectural revision notes
│   ├── topic-1-1-ioc.md
│   ├── topic-1-2-application-context.md
│   └── topic-1-3-injection-styles.md
└── flashcards/             # Anki-compatible CSV flashcard decks
    ├── topic-1-1-ioc.csv
    ├── topic-1-2-application-context.csv
    └── topic-1-3-injection-styles.csv
```

---

## ⚙️ Environment & Tooling

- **Java Version:** Java 21+ (Running on JDK 27)
- **Build Tool:** Apache Maven 3.9+
- **Framework:** Spring Framework 6.x / Spring Boot 3.x
- **Testing:** JUnit Jupiter 5.x
- **Remote Knowledge Base:** Notion API Sync via MCP ("🌱 Spring Boot API Lab")

---

## 🚀 How to Run Exercises

Each topic in `exercises/` is an independent, runnable Maven project with automated JUnit test verification suites.

To test any exercise from the root directory:

```bash
# Test Topic 1.1 (IoC & Ambiguity Resolution)
mvn -f exercises/topic-1-1-ioc/pom.xml test

# Test Topic 1.2 (ApplicationContext Introspection)
mvn -f exercises/topic-1-2-application-context/pom.xml test

# Test Topic 1.3 (Stereotypes & Exception Translation)
mvn -f exercises/topic-1-3-injection-styles/pom.xml test
```
