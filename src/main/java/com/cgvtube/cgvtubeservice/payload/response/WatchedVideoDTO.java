package com.cgvtube.cgvtubeservice.payload.response;

import lombok.*;

import java.sql.Timestamp;

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
    private Timestamp watchedAt;
    private Long userId;
    private String userName;
}
