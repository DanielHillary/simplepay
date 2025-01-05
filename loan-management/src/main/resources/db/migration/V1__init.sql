CREATE TABLE loan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_amount DECIMAL(19, 2),
    interest_rate DECIMAL(19, 2),
    repayment_amount DECIMAL(19, 2),
    loan_term INT,
    borrower_id INT,
    company_reg_number VARCHAR(255),
    company_address TEXT,
    state VARCHAR(255),
    area VARCHAR(255),
    annual_revenue DECIMAL(19, 2),
    disbursement_date DATE,
    repayment_start_date DATE,
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

CREATE TABLE loan_applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    loan_amount DECIMAL(19, 2),
    interest_rate DECIMAL(19, 2),
    loan_term INT,
    borrower_id INT,
    state VARCHAR(255),
    area VARCHAR(255),
    company_reg_number VARCHAR(255),
    company_address TEXT,
    annual_revenue DECIMAL(19, 2),
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    decision_notes TEXT,
    status VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255)
);

CREATE TABLE loan_repayment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount_repaid DECIMAL(19, 2),
    loan_id BIGINT,
    remainder_amount DECIMAL(19, 2),
    repayment_time TIMESTAMP,
    is_full_payment BOOLEAN DEFAULT FALSE,
    schedule_id BIGINT,
    FOREIGN KEY (schedule_id) REFERENCES repayment_schedules(id)
);

CREATE TABLE repayment_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT,
    installment_number INT,
    due_date DATE,
    amount DECIMAL(19, 2),
    is_completed BOOLEAN DEFAULT FALSE,
    loan_repayment_id BIGINT,
    FOREIGN KEY (loan_id) REFERENCES loans(id),
    FOREIGN KEY (loan_repayment_id) REFERENCES loan_repayments(id)
);