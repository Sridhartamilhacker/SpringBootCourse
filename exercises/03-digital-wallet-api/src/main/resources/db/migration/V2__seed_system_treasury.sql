INSERT INTO users (id, email, full_name)
VALUES ('00000000-0000-0000-0000-000000000001', 'treasury@apex.internal', 'Apex System Treasury');

INSERT INTO wallets (id, user_id, wallet_number, balance)
VALUES ('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000001', 'TREASURY_USD', 1000000000.0000);
