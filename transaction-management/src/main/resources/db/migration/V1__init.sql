CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    debit_account_id BIGINT,
    credit_account_id BIGINT,
    user_id BIGINT,
    amount DECIMAL(19, 2),
    description TEXT,
    transaction_time TIMESTAMP
);

CREATE TABLE disbursements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount_disbursed DECIMAL(19, 2),
    time_of_disbursement TIMESTAMP,
    borrower_id BIGINT,
    issuer_id BIGINT,
    account_number INT,
    loan_id BIGINT
);

CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    balance DECIMAL(19, 2),
    account_number INT
)