CREATE TABLE IF NOT EXISTS shorts_tag
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    shorts_id BIGINT,
    tag_id   BIGINT,
    FOREIGN KEY (shorts_id) REFERENCES shorts (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);