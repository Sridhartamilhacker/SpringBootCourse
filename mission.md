# Current Mission: Part 2 — Deep Dive 3: Behind the Scenes of Database Transactions (`@Transactional`)!

**Active Mission:** The "All-or-Nothing" Safety Net (`@Transactional` & Spring Dynamic Proxies)  
**Learning Style:** The Feynman Technique  

### Goal:
Imagine transferring $100 from Account A to Account B:
1. Deduct $100 from Account A.
2. *(Server crashes / database error!)*
3. Add $100 to Account B.
If step 2 fails, Account A lost $100 and Account B never got it! The money vanished into thin air!
In this deep dive, we will learn:
1. **The Feynman Analogy:** The "Bank Teller / Atomic Undo" Contract (ACID).
2. **What `@Transactional` actually does:** `BEGIN TRANSACTION`, `COMMIT`, and `ROLLBACK`.
3. **The "Stunt Double" (Spring Dynamic Proxy):** How Spring wraps your classes in a proxy to manage transactions without polluting your business code.
4. **Hands-On Test:** Trigger a simulated crash and verify that partial changes are rolled back automatically!

---
### Checklist
- [ ] Understand the Bank Transfer analogy (The All-or-Nothing Rule)
- [ ] Understand `COMMIT` vs `ROLLBACK`
- [ ] Understand the "Stunt Double" (Spring AOP Proxy)
- [ ] Create a transactional method and observe automatic rollback on error!
