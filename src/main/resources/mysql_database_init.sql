create database ipgeodb;

CREATE USER 'appgate'@'localhost' IDENTIFIED BY 'appgate';

GRANT ALL PRIVILEGES ON ipgeodb.* TO 'appgate'@'localhost';