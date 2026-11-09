# Milestones & Conceptual Breakthroughs

*Achievements unlocked through demonstrated mastery and verified bug fixes.*

### 🏆 Badge 2: The Cartographer — ApplicationContext & Component Scanning Mastered
- **Unlocked:** 2026-11-09
- **Competencies Mastered:**
  - Deconstructed why `ApplicationContext` replaced `BeanFactory` via the Fail-Fast eager pre-instantiation principle.
  - Explored the two-phase container boot cycle (`BeanDefinition` blueprints vs live heap singletons).
  - Built programmatic container inspection with `ContainerInspectorService` and verified singleton memory reference identity.
  - Diagnosed `NoSuchBeanDefinitionException` caused by out-of-scan package structure.
  - Understood ASM bytecode parsing mechanics under `@ComponentScan`.
  - Published master notes to Notion and compiled Anki flashcards.

### 🏆 Badge 1: The Death of `new` — Inversion of Control Unlocked
- **Unlocked:** 2026-11-09
- **Competencies Mastered:**
  - Deconstructed why the `new` keyword destroys testability and causes cascading constructor refactoring.
  - Built an authentic decoupled order processing domain adhering to SOLID and Constructor Injection.
  - Diagnosed and resolved `NoUniqueBeanDefinitionException` and `UnsatisfiedDependencyException`.
  - Explored Spring internals in `DefaultListableBeanFactory` candidate resolution algorithm.
  - Published master notes to Notion and compiled Anki flashcards.
