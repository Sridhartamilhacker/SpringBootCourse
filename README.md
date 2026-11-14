# 🌱 Spring Boot Mastery Laboratory

> **Our Approach:** **Learn by building first!** Using the **Feynman Technique**, we break concepts down into simple, real-world analogies with zero overwhelming jargon. We build real, visible web APIs first so you see your code working in the browser, and then later look under the hood at advanced internals.

---

## 🧭 The Road to Mastery

### 🌟 Part 1: The Practical Playground (Beginner Friendly)
- [x] **Step 1: Hello Spring Boot — Your First Running Web API**
- [x] **Step 2: Passing Data — How the Web Talks to Your Code**
- [x] **Step 3: Building a Real In-Memory CRUD API**
- [x] **Step 4: Making Data Permanent (Spring Data JPA & H2 Database)**
- [x] **Step 5: Friendly Error Messages & Status Codes**
- [x] **Step 6: Input Validation & Global Error Handling** *(Completed!)*

### 🔬 Part 2: Looking Under the Hood (Advanced Internals)
- [x] **Deep Dive 1: How Auto-Configuration Works**
- [x] **Deep Dive 2: How Spring Dispatches Web Requests (`DispatcherServlet`)**
- [ ] **Deep Dive 3: Behind the Scenes of Database Transactions (`@Transactional`)**
- [ ] **Deep Dive 4: Bean Lifecycle, Scopes, and Proxies**
- [ ] **Deep Dive 5: Production Readiness with Spring Boot Actuator**

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
