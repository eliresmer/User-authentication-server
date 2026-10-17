CREATE DATABASE secure_auth_db;
USE secure_auth_db;


CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       salt VARCHAR(255) NOT NULL
);