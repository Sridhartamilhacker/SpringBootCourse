# Current Mission: Step 5 — Friendly Error Messages & HTTP Status Codes!

**Active Mission:** Communicating Like a Professional Web API using HTTP Status Codes  
**Learning Style:** The Feynman Technique  
**Goal:** Currently, when an item doesn't exist, our API returns an empty `null` with a `200 OK` (which is misleading!). We will learn how to send real HTTP status codes:
1. `200 OK` — Success!
2. `201 Created` — A brand new item was successfully created!
3. `404 Not Found` — The requested item does not exist!
4. `400 Bad Request` — The user sent invalid data!

---
### Checklist
- [ ] Understand HTTP Status Codes using the "Traffic Light" analogy
- [ ] Learn `ResponseEntity<T>` (the envelope Spring uses to set status codes and headers)
- [ ] Update `TaskController` to return `201 Created` on `POST` and `404 Not Found` when a task ID is missing
- [ ] Test with valid and invalid IDs to see real HTTP status codes live!
