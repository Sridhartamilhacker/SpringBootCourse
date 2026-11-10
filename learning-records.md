# Learning Records

### Session Initialization — 2026-11-09
- **Learner Profile:** Beginner to Spring Boot; basic Java foundation established.
- **Environment:** Linux (x86_64), Java 27, Maven 3.9.11, Gradle 9.8.2.
- **Action:** Initialized workspace, connected to Notion MCP, fetched user's existing page "Spring Boot API Lab" / "Class 01 — Spring Framework, Spring Boot & REST APIs", saved to `sources/class-01-spring-foundations.md`, generated comprehensive production-oriented `curriculum.md` spanning Core IoC to Production Observability. Configured to publish completed topic notes directly to Notion and local `notes/`.
### Session 1 — 2026-11-09: Topic 1.1 Completed (Why Spring & IoC)
- **Status:** Completed (Passed Mastery Gate)
- **Stages Executed:**
  - Stage 1 (Confirm): Calibrated Java foundations and IoC mental model.
  - Stage 2 (Explain Why): Deconstructed the tyranny of `new`, assembly-line mental model, and trade-offs.
  - Stage 3 (Teach Concept): Built crude `MiniContainer` in pure Java; demonstrated Constructor Injection with `final` immutability.
  - Stage 4 (Build Exercise): Built `OrderService`, `StripePaymentGateway`, `EmailNotificationService`, `InMemoryOrderRepository` in `exercises/topic-1-1-ioc/`. Tests passed.
  - Stage 5 (Inject Bug): Injected `PayPalPaymentGateway` causing `NoUniqueBeanDefinitionException`. Learner accurately diagnosed multi-candidate ambiguity.
  - Stage 6 (Go Deeper): Traced `DefaultListableBeanFactory.doResolveDependency()` and `determineAutowireCandidate()` candidate-scoring algorithm.
  - Stage 7 (Debrief): Learner gave an interview-grade explanation comparing `@Primary` and `@Qualifier` trade-offs.
- **Artifacts Generated:**
  - Local Note: `notes/topic-1-1-ioc.md`
  - Flashcards: `flashcards/topic-1-1-ioc.csv`
  - Notion Note: Published to `https://app.notion.com/p/4133bf92ecc8819da393da675e28be18?pvs=204` under "🌱 Spring Boot API Lab"
### Session 2 — 2026-11-09: Topic 1.2 Completed (ApplicationContext & BeanFactory)
- **Status:** Completed (Passed Mastery Gate)
- **Stages Executed:**
  - Stage 1 (Confirm): Calibrated startup time expectations and JVM heap allocation.
  - Stage 2 (Explain Why): Deconstructed the "2 AM production landmine" in BeanFactory lazy loading; explained Fail-Fast eager pre-instantiation.
  - Stage 3 (Teach Concept): Showed `BeanDefinition` metadata blueprint vs singleton heap cache; walked through `refresh()` lifecycle.
  - Stage 4 (Build Exercise): Extended continuous order processing domain in `exercises/topic-1-2-application-context/` with `ContainerInspectorService`; proved singleton reference equality (`ref1 == ref2`).
  - Stage 5 (Inject Bug): Injected `AuditService` in external package `com.mastery.audit`, triggering `NoSuchBeanDefinitionException`. Learner accurately diagnosed missing package scan.
  - Stage 6 (Go Deeper): Explained ASM bytecode scanning in `ClassPathScanningCandidateComponentProvider` without classloading.
  - Stage 7 (Debrief): Learner gave an interview-grade debrief explaining package structure importance and component scanning mechanics.
- **Artifacts Generated:**
  - Local Note: `notes/topic-1-2-application-context.md`
  - Flashcards: `flashcards/topic-1-2-application-context.csv`
  - Notion Note: Published to `https://app.notion.com/p/4133bf92ecc881488a55e450b53c183c?pvs=204` under "🌱 Spring Boot API Lab"
### Session 3 — 2026-11-10: Topic 1.3 Completed (Stereotypes & Injection Styles)
- **Status:** Completed (Passed Mastery Gate)
- **Stages Executed:**
  - Stage 1 (Confirm): Calibrated stereotypes vs generic `@Component` and gut feeling on field injection.
  - Stage 2 (Explain Why): Hospital role analogy; why `@Repository` gets special exception translation; traps of `@Autowired` field injection.
  - Stage 3 (Teach Concept): Proved `@Service` and `@Repository` are meta-annotations wrapping `@Component`; walked through three injection styles side-by-side.
  - Stage 4 (Build Exercise): Extended order domain in `exercises/topic-1-3-injection-styles/` with `@Component` utility (`OrderIdGenerator`), `@Repository` exception translation, and `@Service` constructor injection.
  - Stage 5 (Inject Bug): Injected circular dependency between `OrderService` and `PricingService`, triggering `BeanCurrentlyInCreationException`. Learner accurately identified unneeded coupling.
  - Stage 6 (Go Deeper): Explained `singletonsCurrentlyInCreation` tracking set in `DefaultSingletonBeanRegistry` and JVM constructor mechanics.
  - Stage 7 (Debrief): Learner gave an interview-grade debrief explaining stereotype semantic roles, `@Repository` proxy exception translation, and circular dependency resolution.
- **Artifacts Generated:**
  - Local Note: `notes/topic-1-3-injection-styles.md`
  - Flashcards: `flashcards/topic-1-3-injection-styles.csv`
  - Notion Note: Published to `https://app.notion.com/p/4143bf92ecc8810db8d8e2025d9adefb?pvs=204` under "🌱 Spring Boot API Lab"
- **Next Mission:** Topic 1.4: Java Configuration & `@Bean` Methods.
