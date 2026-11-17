# Current Mission: ApexWallet — Phase 1: Infrastructure & Project Scaffolding

**Active Mission:** Spinning up PostgreSQL & Initializing Project Structure  
**Learning Style:** The Feynman Technique (One step at a time, Learner writes all code)  

### Current Step: Step 1.1 — Spin up Containerized PostgreSQL 16
Before we write any Spring Boot code, we need a real database running.
We will launch a dedicated, isolated PostgreSQL 16 container using **Podman** (which is rootless, fast, and OCI-compliant).

---
### Phase 1 Checklist
- [x] **Step 1.1:** Spin up PostgreSQL 16 container via Podman (Verified: container `apex-postgres` is UP!)
- [x] **Step 1.2:** Verify database connectivity and credentials using `psql` (Verified: `apex_wallet` database active!)
- [ ] **Step 1.3:** Create project directory `exercises/03-digital-wallet-api`
- [ ] **Step 1.4:** Configure enterprise `pom.xml` with dependencies (Postgres, Flyway, Validation, OpenAPI, Actuator)
- [ ] **Step 1.5:** Create `application.yml` and test database connection on application boot!
