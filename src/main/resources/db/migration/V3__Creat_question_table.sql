CREATE TABLE question
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50),
    discribe TEXT,
    creater INT,
    comment_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    tags VARCHAR(256)
);