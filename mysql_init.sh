#!/bin/bash

# Connect to a mysql server running on docker-machine named 'Char' on localhost:
# Username: root
# Password: doge

# It already has a database called swingDB

mysql -h $(docker-machine ip Char) -u root -pdoge swingyDB

CREATE TABLE hero_table
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	hero_name VARCHAR(30) NOT NULL,
	hero_class VARCHAR(30) NOT NULL,
	hero_level INT NOT NULL,
	map_level INT NOT NULL,

	current_exp INT NOT NULL,
	attack_points INT NOT NULL,
	defense_points INT NOT NULL,
	current_health INT NOT NULL,
	max_health INT NOT NULL,

	has_weapon BOOLEAN NOT NULL,
	weapon_name VARCHAR(30),
	weapon_attack_modifier INT,
	weapon_defense_modifier INT,
	weapon_health_modifier INT,

	has_armor BOOLEAN NOT NULL,
	armor_name VARCHAR(30),
	armor_attack_modifier INT,
	armor_defense_modifier INT,
	armor_health_modifier INT,

	has_helmet BOOLEAN NOT NULL,
	helmet_name VARCHAR(30),
	helmet_attack_modifier INT,
	helmet_defense_modifier INT,
	helmet_health_modifier INT
);

CREATE TABLE `simple_table` (`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, `hero_name` VARCHAR(30) NOT NULL, `hero_class`	VARCHAR(30) NOT NULL, `hero_level` INT);

INSERT INTO `simple_table` (`hero_name`, `hero_class`, `hero_level`) VALUES
	('Jojo',		'warrior',	-3),
	('Bobbo',		'thief',	42);

INSERT INTO hero_table (hero_name, hero_class, hero_level, current_exp, attack_points, defense_points, current_health, max_health, map_level,
	has_weapon, weapon_name, weapon_attack_modifier, weapon_defense_modifier, weapon_health_modifier,
	has_armor, armor_name, armor_attack_modifier, armor_defense_modifier, armor_health_modifier,
	has_helmet, helmet_name, helmet_attack_modifier, helmet_defense_modifier, helmet_health_modifier) VALUES
	('Jojo', 'warrior', -3, 1, 0, 1, 2, 3, 4, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);

