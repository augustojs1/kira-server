USE `kira_database`;

CREATE TABLE `users` (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         role ENUM('USER', 'ADMIN') NOT NULL
);

CREATE TABLE `boards` (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id),
                          title VARCHAR(100) NOT NULL UNIQUE,
                          description VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `board_members` (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 user_id INT NOT NULL,
                                 FOREIGN KEY (user_id) REFERENCES users(id),
                                 board_id INT NOT NULL,
                                 FOREIGN KEY (board_id) REFERENCES boards(id),
                                 role ENUM('USER', 'ADMIN') NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `invites` (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           inviter_id INT NOT NULL,
                           FOREIGN KEY (inviter_id) REFERENCES users(id),
                           invited_id INT NOT NULL,
                           FOREIGN KEY (invited_id) REFERENCES users(id),
                           board_id INT NOT NULL,
                           FOREIGN KEY (board_id) REFERENCES boards(id),
                           role ENUM('USER', 'ADMIN') NOT NULL,
                           accepted BOOLEAN NOT NULL DEFAULT FALSE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `status` (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          board_id INT NOT NULL,
                          FOREIGN KEY (board_id) REFERENCES boards(id),
                          title VARCHAR(100) NOT NULL,
                          position INT NOT NULL,
                          CONSTRAINT chk_position CHECK (position >= 1 AND position <= 8),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `tasks` (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         assigned_id INT,
                         FOREIGN KEY (assigned_id) REFERENCES users(id),
                         board_id INT NOT NULL,
                         FOREIGN KEY (board_id) REFERENCES boards(id),
                         status_id INT NOT NULL,
                         FOREIGN KEY (status_id) REFERENCES `status`(id),
                         title VARCHAR(150) NOT NULL,
                         description VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `task_comment` (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                task_id INT,
                                FOREIGN KEY (task_id) REFERENCES tasks(id),
                                board_id INT NOT NULL,
                                FOREIGN KEY (board_id) REFERENCES boards(id),
                                content VARCHAR(255) NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);