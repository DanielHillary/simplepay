CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    user_id VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    photos TEXT,
    phone_number VARCHAR(255),
    reset_otp VARCHAR(255),
    user_time_zone VARCHAR(255),
    reset_otp_request_time TIMESTAMP,
    otp_request_time TIMESTAMP,
    email_verified BOOLEAN DEFAULT FALSE,
    verification_otp VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(225),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

CREATE TABLE token (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE,
    token_type ENUM('BEARER') NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    expired BOOLEAN DEFAULT FALSE,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);