CREATE TABLE IF NOT EXISTS tag
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name TEXT
);

CREATE TABLE IF NOT EXISTS video
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       TEXT,
    description TEXT ,
    video_url   TEXT,
    thumbnail   TEXT ,
    release_date TIMESTAMP,
    is_private BOOLEAN,
    views       BIGINT DEFAULT 0,
    likes      BIGINT DEFAULT 0,
    dislikes  BIGINT DEFAULT 0,
    duration TEXT,
    is_shorts BOOLEAN,
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