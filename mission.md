# Current Mission: ApexWallet — Phase 2: Database Schema & Flyway Migrations

**Active Mission:** Version-Controlled Database Schema Engineering  
**Learning Style:** The Feynman Technique (One step at a time, Learner writes all code)  

### Status: Phase 1 COMPLETED! 🚀
- PostgreSQL 16 container running and healthy on port 5432.
- Gradle project initialized and compiling with Java 27/21.
- HikariCP pool initialized and connected to PostgreSQL.
- SpringDoc OpenAPI initialized.

---

### Phase 2: Database Schema & Flyway Migrations
In production, developers NEVER let Hibernate touch database tables (`ddl-auto: validate`).
Instead, every database table, column, index, and check constraint is written in **version-controlled SQL migration scripts**.

### Phase 2 Checklist:
- [ ] **Step 2.1:** Understand Flyway naming conventions (`V1__...sql`) and the `flyway_schema_history` table.
- [ ] **Step 2.2:** Author `V1__create_wallet_and_ledger_tables.sql`:
  - `users` table
  - `wallets` table (with `CHECK (balance >= 0.0000)` and `version` for optimistic locking)
  - `transactions` table (master audit records)
  - `ledger_entries` table (immutable debits and credits)
  - `idempotency_records` table (24-hour request deduplication)
- [ ] **Step 2.3:** Author `V2__seed_system_treasury.sql`:
  - Seed internal system treasury wallet.
- [ ] **Step 2.4:** Boot application to run migrations and inspect PostgreSQL tables live via `psql`!
