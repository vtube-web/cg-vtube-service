package com.cgvtube.cgvtubeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_liked_video")
public class UserLikedVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "liked_at")
    private LocalDateTime likedAt;

    @Column(name = "liked_status")
    private boolean likedStatus;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "video_id",
            nullable = false,
            referencedColumnName = "id")
    private Video video;

    public UserLikedVideo(User user, Video video, LocalDateTime now) {
        this.user = user;
        this.video = video;
        this.likedAt = now;
    }
}
