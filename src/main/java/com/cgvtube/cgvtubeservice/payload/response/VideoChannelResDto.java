package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoChannelResDto {
    private Long id;
    private Long views;
    private Long likes;
    private Long dislikes;
    private String thumbnail;
    private String title;
    private String description;
    private String videoUrl;
    private Boolean isPrivate;
    private LocalDateTime releaseDate;
    private LocalDateTime createAt;
    private Long totalComment;
    private String duration;
}
