use loginuser;

CREATE TABLE users (
    user_id INT(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE tasks (
    task_id INT(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    task_description VARCHAR(255) NOT NULL,
    estimated_hours INT(11) NOT NULL,
);

CREATE TABLE efforts (
    effort_id INT(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    task_id INT(11) NOT NULL,
    effort_name VARCHAR(255) NOT NULL,
    effort_description VARCHAR(255) NOT NULL,
    effort_hours INT(11) NOT NULL,
);