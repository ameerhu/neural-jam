
CREATE DATABASE `neural_jam`;

CREATE TABLE `joke`(
    `id` VARCHAR(36) NOT NULL,
    `joke` INT(1) NOT NULL,
    `created_date` TIMESTAMP NULL,
    `last_modified_date` TIMESTAMP NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHAR SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

