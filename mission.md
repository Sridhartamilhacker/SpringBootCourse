# Current Mission: ApexWallet — Phase 6: Public REST Controllers, RFC 7807 Error Handling & Swagger UI

**Active Mission:** Public REST Interface, Machine-Readable Error Contracts, & Interactive API Documentation  
**Engineering Standard:** OpenAPI 3 / Swagger UI, RFC 7807 `ProblemDetail`, Network-Resilient Idempotent Endpoints  

---

### Status: Phases 1, 2, 3, 4, & 5 COMPLETED! 🚀
- [x] **Phase 1: Environment & Scaffolding**
  - PostgreSQL 16 container running on port 5432.
  - Gradle configured with Spring Boot 4 / Java 27 / SpringDoc OpenAPI.
- [x] **Phase 2: Database Schema & Flyway Migrations**
  - Schema migrations V1 & V2 executed live with System Treasury ($1B reserve).
- [x] **Phase 3: Domain Entities & Repositories**
  - 5 JPA entities and 5 repositories verified under `ddl-auto: validate`.
- [x] **Phase 4: Business Logic & Double-Entry Ledger Engine**
  - Double-entry payment engine with mathematical ledger parity and optimistic concurrency control.
- [x] **Phase 5: 24-Hour Distributed Idempotency Engine & DTOs**
  - 6 Request DTOs and 3 Response DTOs using immutable Java `record`s.
  - `IdempotencyService` guaranteeing at-most-once execution over a 24-hour window.

---

### Phase 6: Public REST API, Swagger UI & Verification
Now we expose our bulletproof ledger to the outside world through standard RESTful controllers.

### Phase 6 Checklist:
- [ ] **Step 6.1: RFC 7807 Centralized Exception Handler (`GlobalExceptionHandler`)**
  - Standardized `ProblemDetail` responses for:
    - `WalletNotFoundException`, `UserNotFoundException` -> `404 Not Found`
    - `InsufficientFundsException`, `WalletInactiveException`, `InvalidTransferException` -> `400 Bad Request` / `422 Unprocessable Entity`
    - `MethodArgumentNotValidException` -> `400 Bad Request` with field validation errors
    - `OptimisticLockException` / `ObjectOptimisticLockingFailureException` -> `409 Conflict`
- [ ] **Step 6.2: Wallet Controller (`WalletController`)**
  - `POST /api/users/{userId}/wallets`: create wallet for user.
  - `GET /api/wallets/{walletNumber}`: get single wallet.
  - `GET /api/users/{userId}/wallets`: list all wallets owned by user.
  - `PATCH /api/wallets/{walletNumber}/status`: freeze/unfreeze/close wallet.
- [ ] **Step 6.3: Transaction & Payment Controller (`PaymentController`)**
  - `POST /api/payments/transfer`: P2P transfer with `@RequestHeader(value = "Idempotency-Key", required = false)`.
  - `POST /api/payments/deposit`: deposit from Treasury with idempotency support.
  - `POST /api/payments/withdraw`: withdraw to Treasury with idempotency support.
  - `GET /api/wallets/{walletNumber}/statement`: get chronological ledger history.
- [ ] **Step 6.4: Interactive Verification via Swagger UI & Live cURL**
  - Open `http://localhost:8080/swagger-ui.html`.
  - Test wallet creation, deposits, P2P transfers, idempotency replays, and race condition handling!
