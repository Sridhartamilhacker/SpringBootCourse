# Current Mission: ApexWallet — Phase 5: 24-Hour Distributed Idempotency Engine & DTO Contracts

**Active Mission:** API Contracts (DTOs), Input Validation, & Network Resilience via Idempotency Keys  
**Engineering Standard:** Clean Architecture Separation (No Entities Exposed in API), RFC 7807 Error Preparedness, 24h Deduplication  

---

### Status: Phases 1, 2, 3, & 4 COMPLETED! 🚀
- [x] **Phase 1: Environment & Scaffolding**
  - PostgreSQL 16 container running and healthy on port 5432.
  - Gradle project configured with Spring Boot 4 / Java 27 / SpringDoc OpenAPI.
- [x] **Phase 2: Database Schema & Flyway Migrations**
  - `V1` and `V2` migrations executed; System Treasury initialized.
- [x] **Phase 3: Domain Entities & Repositories**
  - All 5 JPA entities and 5 repositories validated by Hibernate.
- [x] **Phase 4: Business Logic & Double-Entry Ledger Engine**
  - Domain exceptions: `WalletNotFoundException`, `InsufficientFundsException`, `WalletInactiveException`, `InvalidTransferException`.
  - `WalletService`: Account lifecycle & status management with `@Transactional(readOnly = true)`.
  - `PaymentService`: Double-entry accounting engine ($\sum Debits + \sum Credits = 0$) with optimistic concurrency defense and CGLIB proxy-aware `@Transactional` methods.

---

### Phase 5: 24-Hour Idempotency Engine & DTO Contracts
In production financial networks, network drops happen. If a mobile app retries an HTTP POST request because of a timeout, the API must never charge the customer twice. 

### Phase 5 Checklist:
- [ ] **Step 5.1: Request and Response DTOs (`com.apex.wallet.dto`)**
  - `CreateWalletRequest`: `userId` (`@NotNull`), `currency` (`@NotBlank`, e.g. "USD").
  - `TransferRequest`: `sourceWalletNumber`, `targetWalletNumber`, `amount` (`@NotNull`, `@Positive`), `referenceNote`.
  - `DepositRequest`: `targetWalletNumber`, `amount` (`@NotNull`, `@Positive`), `referenceNote`.
  - `WithdrawRequest`: `sourceWalletNumber`, `amount` (`@NotNull`, `@Positive`), `referenceNote`.
  - `UpdateStatusRequest`: `status` (`@NotNull`).
  - `WalletResponse`: decoupled representation of `Wallet`.
  - `TransactionResponse`: decoupled representation of `Transaction`.
  - `LedgerEntryResponse`: statement entry.
- [ ] **Step 5.2: The Idempotency Service (`IdempotencyService`)**
  - Check if `Idempotency-Key` exists and is still valid (`expiresAt.isAfter(OffsetDateTime.now())`).
  - Store response body and status code for 24 hours.
  - Return cached response on replay without re-executing transfer.
