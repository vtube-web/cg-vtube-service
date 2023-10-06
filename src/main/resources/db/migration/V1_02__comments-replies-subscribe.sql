CREATE TABLE IF NOT EXISTS comments
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    video_id  BIGINT,
    user_id   BIGINT,
    likes       BIGINT    DEFAULT 0,
    dislikes    BIGINT    DEFAULT 0,
    content   TEXT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS replies
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT,
    likes       BIGINT    DEFAULT 0,
    dislikes    BIGINT    DEFAULT 0,
    content    TEXT,
    comment_id BIGINT,
    create_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (comment_id) REFERENCES comments(id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);


CREATE TABLE IF NOT EXISTS subscribe
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT,
    subscriber_id BIGINT,
    subscribe_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (subscriber_id) REFERENCES user (id)
);