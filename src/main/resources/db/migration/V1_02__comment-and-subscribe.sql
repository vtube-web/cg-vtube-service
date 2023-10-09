CREATE TABLE IF NOT EXISTS comments
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    video_id         BIGINT,
    user_id          BIGINT,
    `like`           BIGINT,
    `dislike`        BIGINT,
    content          TEXT,
    createAt         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parentComment_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (parentComment_id) REFERENCES comments (id)
);

CREATE TABLE IF NOT EXISTS subscribe
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT,
    subscriber_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (subscriber_id) REFERENCES user (id)
);
