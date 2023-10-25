package com.cgvtube.cgvtubeservice.payload.response;

import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchedVideoDTO {
    private Long videoId;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnail;
    private Long views;
    private String duration;
    private LocalDateTime watchedAt;
    private LocalDateTime createAt;
    private Long userId;
    private String userName;
}
