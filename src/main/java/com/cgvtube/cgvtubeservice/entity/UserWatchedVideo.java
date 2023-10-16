package com.cgvtube.cgvtubeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_watched_video")
public class UserWatchedVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "watched_at")
    private LocalDateTime watchedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "video_id",
            nullable = false,
            referencedColumnName = "id")
    private Video video;

    public UserWatchedVideo(Video video, User user, LocalDateTime now) {
        this.video = video;
        this.user = user;
        this.watchedAt = now;
    }
}
