# Current Mission: ApexWallet — Phase 4: Business Logic & Double-Entry Ledger Engine

**Active Mission:** Core Financial Engine, Atomic Transfers, & Double-Entry Bookkeeping  
**Engineering Standard:** ACID Transactions (`@Transactional`), Mathematical Ledger Parity ($\sum Debit + \sum Credit = 0$), Concurrency Safeguards  

---

### Status: Phases 1, 2, & 3 COMPLETED! 🚀
- [x] **Phase 1: Environment & Scaffolding**
  - PostgreSQL 16 container running and healthy on port 5432.
  - Gradle project configured with Spring Boot 4 / Java 27 / SpringDoc OpenAPI.
- [x] **Phase 2: Database Schema & Flyway Migrations**
  - `V1__create_wallet_and_ledger_tables.sql` & `V2__seed_system_treasury.sql`.
  - Automated migrations on boot via `spring-boot-starter-flyway`.
- [x] **Phase 3: Domain Entities & Repositories**
  - 4 Model Enums: `WalletStatus`, `TransactionType`, `TransactionStatus`, `EntryType`.
  - 5 JPA Entities: `User`, `Wallet` (`@Version`), `Transaction`, `LedgerEntry`, `IdempotencyRecord`.
  - 5 Spring Data JPA Repositories with custom query methods.
  - Verified 100% schema agreement with PostgreSQL under `ddl-auto: validate`.

---

### Phase 4: Business Logic & Double-Entry Ledger Engine
This is the core financial heart of ApexWallet. In this phase, we ensure money is never created or destroyed arbitrarily, balances never go negative, and all operations are atomic and audit-compliant.

### Phase 4 Checklist:
- [ ] **Step 4.1: Domain Exceptions**
  - `WalletNotFoundException`, `InsufficientFundsException`, `WalletFrozenException`, `InvalidTransferException`.
- [ ] **Step 4.2: Wallet Management Service (`WalletService`)**
  - `createWallet(UUID userId, String currency)`: generates unique wallet number, saves wallet with zero balance.
  - `getWalletByNumber(String walletNumber)`: returns wallet or throws exception.
  - `updateWalletStatus(String walletNumber, WalletStatus status)`: freeze/unfreeze account.
- [ ] **Step 4.3: The Double-Entry Ledger Transfer Service (`PaymentService`)**
  - Atomic `@Transactional` execution:
    1. Validation: Verify source & destination wallets exist, are `ACTIVE`, and source has sufficient balance.
    2. Master Record: Persist master `Transaction` with status `PENDING`.
    3. Immutable Ledger Entries:
       - Debit Entry: source wallet balance reduced, `DEBIT` ledger entry created with `running_balance`.
       - Credit Entry: destination wallet balance increased, `CREDIT` ledger entry created with `running_balance`.
    4. Balance Updates: update wallet balances (protected by `@Version` optimistic locking).
    5. Finalize: mark master `Transaction` as `SUCCESS`.
- [ ] **Step 4.4: System Treasury Operations (Deposit & Withdrawal)**
  - `deposit(String targetWalletNumber, BigDecimal amount)`: Debit `TREASURY_USD`, Credit `targetWalletNumber`.
  - `withdraw(String sourceWalletNumber, BigDecimal amount)`: Debit `sourceWalletNumber`, Credit `TREASURY_USD`.
