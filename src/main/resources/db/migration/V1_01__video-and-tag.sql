CREATE TABLE IF NOT EXISTS tag
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name TEXT
);

CREATE TABLE IF NOT EXISTS video
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       TEXT,
    description TEXT,
    video_url   TEXT,
    views       BIGINT,
    `like`      BIGINT,
    `dislike`   BIGINT,
    user_id     BIGINT,
    create_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS video_tag
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    video_id BIGINT,
    tag_id   BIGINT,
    FOREIGN KEY (video_id) REFERENCES video (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);
