CREATE TABLE IF NOT EXISTS shorts
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    title        TEXT,
    shorts_url    TEXT,
    is_private   BOOLEAN,
    views        BIGINT,
    likes        BIGINT    DEFAULT 0,
    dislikes     BIGINT    DEFAULT 0,
    user_id      BIGINT,
    create_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS comments_shorts
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    shorts_id  BIGINT,
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
    comments_short_id BIGINT,
    create_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (comments_short_id) REFERENCES comments_shorts(id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);