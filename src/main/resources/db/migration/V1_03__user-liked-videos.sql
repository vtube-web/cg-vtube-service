CREATE TABLE IF NOT EXISTS user_liked_video
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id  BIGINT,
    video_id BIGINT,
    create_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (video_id) REFERENCES video (id),
    UNIQUE (user_id, video_id)
);
