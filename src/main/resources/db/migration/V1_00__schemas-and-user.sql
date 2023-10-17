CREATE DATABASE IF NOT EXISTS vtube_webapp;
USE vtube_webapp;

CREATE TABLE IF NOT EXISTS user
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    email        varchar(255) UNIQUE NOT NULL,
    password     varchar(255)        NOT NULL,
    user_name    varchar(255)        NOT NULL,
    avatar       varchar(255),
    created_at   datetime
);

CREATE TABLE IF NOT EXISTS role
(
    id        bigint PRIMARY KEY AUTO_INCREMENT,
    role_name varchar(20)
);

CREATE TABLE IF NOT EXISTS user_role
(
    id      bigint PRIMARY KEY AUTO_INCREMENT,
    user_id bigint,
    role_id bigint,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);