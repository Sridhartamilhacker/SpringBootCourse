# Current Mission: ApexWallet — Phase 3: Domain Entities & JPA Repositories

**Active Mission:** Production JPA Entity Modeling & Data Access Layer  
**Engineering Standard:** Clean Architecture, Strict Schema Validation (`ddl-auto: validate`), Optimistic Locking  

---

### Status: Phase 1 & 2 COMPLETED! 🚀
- [x] **Phase 1: Environment & Scaffolding**
  - PostgreSQL 16 container running and healthy on port 5432.
  - Gradle project configured with Spring Boot 4 / Java 27 / SpringDoc OpenAPI.
  - HikariCP pool initialized and connected to `apex_wallet`.
- [x] **Phase 2: Database Schema & Flyway Migrations**
  - `V1__create_wallet_and_ledger_tables.sql` authored with double-entry constraints.
  - `V2__seed_system_treasury.sql` authored with $1B reserve treasury account.
  - `spring-boot-starter-flyway` added and live migration executed into PostgreSQL.

---

### Phase 3: Domain Entities & Repositories
Since `ddl-auto: validate` is active, Hibernate strictly verifies that every entity, column type, precision, and constraint precisely matches the PostgreSQL tables created by Flyway. Any mismatch will halt application startup.

### Phase 3 Checklist:
- [ ] **Step 3.1: Enums & Shared Types**
  - `WalletStatus` (`ACTIVE`, `FROZEN`, `CLOSED`)
  - `TransactionType` (`DEPOSIT`, `WITHDRAWAL`, `TRANSFER`)
  - `TransactionStatus` (`PENDING`, `SUCCESS`, `FAILED`)
  - `EntryType` (`DEBIT`, `CREDIT`)
- [ ] **Step 3.2: Domain Entities (`com.apex.wallet.model`)**
  - `User`: UUID ID, unique email, full_name, created_at.
  - `Wallet`: UUID ID, user_id, unique wallet_number, balance (`BigDecimal`), currency, status, `@Version` optimistic locking counter.
  - `Transaction`: UUID ID, transaction_type, status, reference_note, created_at.
  - `LedgerEntry`: UUID ID, transaction_id, wallet_id, entry_type, amount (`BigDecimal`), running_balance (`BigDecimal`), created_at.
  - `IdempotencyRecord`: idempotency_key (`@Id`), transaction_id, response_status, response_body, created_at, expires_at.
- [ ] **Step 3.3: Spring Data JPA Repositories (`com.apex.wallet.repository`)**
  - `UserRepository`: `findByEmail(String email)`
  - `WalletRepository`: `findByWalletNumber(String walletNumber)`, `findAllByUserId(UUID userId)`
  - `TransactionRepository`: Standard CRUD
  - `LedgerEntryRepository`: `findAllByWalletIdOrderByCreatedAtDesc(UUID walletId)`
  - `IdempotencyRecordRepository`: `findById(String idempotencyKey)`
- [ ] **Step 3.4: Boot & Hibernate Schema Validation**
  - Boot the application and verify that Hibernate's `ddl-auto: validate` executes successfully against PostgreSQL with zero schema divergence errors!
