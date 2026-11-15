# Current Mission: Part 2 — Deep Dive 4: Bean Lifecycle, Scopes, and Proxies!

**Active Mission:** The Lifecycle of a Spring Bean (`@PostConstruct`, `@PreDestroy`, and Scopes)  
**Learning Style:** The Feynman Technique  

### Goal:
Have you ever wondered:
- When you annotate a class with `@Service` or `@Component`, who creates it, and when?
- If 100 users visit your website at the same second, do they share 1 single Service object or does Spring create 100 separate objects?
- How does Spring run custom setup code right after creating an object, and cleanup code right before the server shuts down?

In this deep dive, we will learn:
1. **The Feynman Analogy:** The Factory Assembly Line & The Hotel Room (Singleton vs Prototype).
2. **Bean Lifecycle Phases:** Instantiation -> Dependency Injection -> `@PostConstruct` -> Active Service -> `@PreDestroy` on shutdown.
3. **Bean Scopes:** Singleton (default, 1 instance per app) vs Prototype (new instance every time).
4. **Hands-On Verification:** Log lifecycle hook events in the console as Spring starts up and gracefully shuts down!

---
### Checklist
- [ ] Understand Bean Scopes: Singleton vs Prototype
- [ ] Understand the Bean Lifecycle sequence (Birth, Life, Death)
- [ ] Add `@PostConstruct` and `@PreDestroy` to a component and watch the lifecycle in terminal logs
- [ ] Test Singleton vs Prototype identity with `System.identityHashCode()`
