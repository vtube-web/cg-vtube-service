CREATE TABLE IF NOT EXISTS playlist
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    title    NVARCHAR(255),
    user_id  BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS playlist_video
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    playlist_id BIGINT,
    video_id    BIGINT,
    FOREIGN KEY (playlist_id) REFERENCES playlist (id),
    FOREIGN KEY (video_id) REFERENCES video (id)
);

insert into `user` (email, password, user_name, avatar, created_at)
values ('trung15@gmail.com', '$2a$04$N2x9LeH13862DhuSGqyPsecE.HvQ1pXLu8Oha9oE85RIVFXmfT1M.', 'Trunghehe',
        'https://scontent.fsgn13-3.fna.fbcdn.net/v/t39.30808-6/327246234_5822991967749010_5960641830009025059_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=5f2048&_nc_ohc=HPAgEt6qnP0AX-9UeyY&_nc_ht=scontent.fsgn13-3.fna&oh=00_AfBzHbaIfD9j8WGCDFtwybRw6jImYhA5BvhVjfleGV4nJQ&oe=6536DFD7',
        '2019-06-25 07:30:20');
