CREATE DATABASE IF NOT EXISTS opportuna;
USE opportuna;

CREATE TABLE IF NOT EXISTS opportunities (
    id BIGINT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    organization VARCHAR(150) NOT NULL,
    type VARCHAR(50) NOT NULL,
    deadline DATE NOT NULL,
    duration_weeks INT NOT NULL,
    stipend INT NOT NULL,
    required_skills VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS applications (
    id BIGINT PRIMARY KEY,
    opportunity_id BIGINT NOT NULL,
    applied_on DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    note VARCHAR(500),
    CONSTRAINT fk_application_opportunity
        FOREIGN KEY (opportunity_id) REFERENCES opportunities(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
