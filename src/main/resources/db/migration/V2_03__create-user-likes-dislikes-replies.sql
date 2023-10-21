CREATE TABLE IF NOT EXISTS user_likes_dislikes_replies
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    replies_id BIGINT,
    likes BOOLEAN DEFAULT FALSE,
    dislikes BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (replies_id) REFERENCES replies (id)
    )