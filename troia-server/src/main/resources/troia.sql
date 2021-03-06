CREATE DATABASE Troia 
 CHARACTER SET utf8
 DEFAULT CHARACTER SET utf8
 COLLATE utf8_general_ci
 DEFAULT COLLATE utf8_general_ci;

USE Troia;

CREATE TABLE Projects (
id VARCHAR(100) NOT NULL PRIMARY KEY, 
kind VARCHAR(25) NOT NULL, 
data LONGTEXT NOT NULL,
results LONGTEXT,
initializationData LONGTEXT,
model LONGTEXT,
last_use TIMESTAMP) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE INDEX idIndex on Projects (id);

