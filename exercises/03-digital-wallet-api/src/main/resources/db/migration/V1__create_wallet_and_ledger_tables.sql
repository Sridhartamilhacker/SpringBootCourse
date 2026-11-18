CREATE TABLE users(
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallets(
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    wallet_number VARCHAR(50) UNIQUE NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    balance NUMERIC(19,4) NOT NULL DEFAULT 0.0000,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_wallet_status CHECK (status IN ('ACTIVE','FROZEN','CLOSED')),
    CONSTRAINT chk_wallet_balance CHECK(balance >= 0.0000)
);

CREATE TABLE transactions(
    id UUID PRIMARY KEY,
    transaction_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL,
    reference_note VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ledger_entries(
    id UUID PRIMARY KEY,
    transaction_id UUID REFERENCES transactions(id),
    wallet_id UUID REFERENCES wallets(id),
    entry_type VARCHAR(10) NOT NULL,
    amount NUMERIC(19,4) NOT NULL,
    running_balance(19,4) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_entry_type CHECK(entry_type IN ('DEBIT','CREDIT'))
    CONSTRAINT chk_amount CHECK(amount >= 0.0000)
);

CREATE TABLE idempotency_records(
    id UUID PRIMARY KEY,
    transaction_id UUID REFERENCES transactions(id),
    response_status INT NOT NULL,
    response_body TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
);

CREATE INDEX idx_wallets_user_id ON wallets(user_id);
CREATE INDEX idx_ledger_wallet_id ON ledger_entries(wallet_id);
CREATE INDEX idx_ledger_created_at ON ledger_entries(created_at);
CREATE INDEX idx_idempotency_expires ON idempotency_records(expires_at);
