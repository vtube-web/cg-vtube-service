CREATE TABLE IF NOT EXISTS user_watched_video
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id  BIGINT,
    video_id BIGINT,
    watched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (video_id) REFERENCES video (id)
);