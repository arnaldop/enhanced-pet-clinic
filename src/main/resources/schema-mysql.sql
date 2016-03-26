CREATE TABLE `users` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `enabled` BOOLEAN DEFAULT TRUE NOT NULL,
  `account_expired` BOOLEAN DEFAULT FALSE NOT NULL,
  `account_locked` BOOLEAN DEFAULT FALSE NOT NULL,
  `credentials_expired` BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`username`),
  UNIQUE (`email`)
);

CREATE TABLE `authorities` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`authority`)
);

CREATE TABLE `user_authorities` (
  `user_id` INTEGER NOT NULL,
  `authority_id` INTEGER NOT NULL,
  PRIMARY KEY (`user_id`, `authority_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`)
);

CREATE TABLE `persistent_logins` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`, `series`),
  FOREIGN KEY (`username`) REFERENCES `users` (`username`)
);

-- --------------------------------------------------------------------------

CREATE TABLE `vets` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50),
  `last_name` VARCHAR(50),
  PRIMARY KEY (`id`)
);

CREATE TABLE `specialties` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250),
  PRIMARY KEY (`id`)
);

CREATE TABLE `vet_specialties` (
  `vet_id` INTEGER NOT NULL,
  `specialty_id` INTEGER NOT NULL,
  FOREIGN KEY (`vet_id`) REFERENCES `vets` (`id`),
  FOREIGN KEY (`specialty_id`) REFERENCES `specialties` (`id`)
);

CREATE TABLE `pet_types` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50),
  PRIMARY KEY (`id`)
);

CREATE TABLE `owners` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50),
  `last_name` VARCHAR(50),
  `address` VARCHAR(150),
  `city` VARCHAR(50),
  `telephone` VARCHAR(50),
  PRIMARY KEY (`id`)
);

CREATE TABLE `pets` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50),
  `birth_date` DATE,
  `pet_type_id` INTEGER NOT NULL,
  `owner_id` INTEGER NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`pet_type_id`) REFERENCES `pet_types` (`id`),
  FOREIGN KEY (`owner_id`) REFERENCES `owners` (`id`)
);

CREATE TABLE `visits` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `pet_id` INTEGER NOT NULL,
  `visit_date` DATE,
  `description` VARCHAR(500),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`)
);
