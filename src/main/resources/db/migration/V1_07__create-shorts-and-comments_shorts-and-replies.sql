CREATE TABLE IF NOT EXISTS comments_shorts
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

CREATE TABLE IF NOT EXISTS replies_shorts
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT,
    likes       BIGINT    DEFAULT 0,
    dislikes    BIGINT    DEFAULT 0,
    content    TEXT,
    comment_shorts_id BIGINT,
    create_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (comment_shorts_id) REFERENCES comments_shorts(id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);