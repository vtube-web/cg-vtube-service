package com.cgvtube.cgvtubeservice.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedVideoDTO {
    private Long videoId;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnail;
    private Long views;
    private LocalDateTime likedAt;
    private LocalDateTime createAt;
    private Long userId;
    private String userName;
    private boolean likedStatus;
    private String userLike;
}
