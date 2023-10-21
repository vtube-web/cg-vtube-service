CREATE TABLE IF NOT EXISTS user_likes_dislikes_comments
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    comments_id BIGINT,
    likes BOOLEAN DEFAULT FALSE,
    dislikes BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (comments_id) REFERENCES comments (id)
)