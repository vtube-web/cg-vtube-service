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
public class VideoResponseDto {
    private Long id;
    private Long views;
    private Long likes;
    private Long dislikes;
    private String thumbnail;
    private String title;
    private String description;
    private String videoUrl;
    private String duration;
    private LocalDateTime createAt;
    private LocalDateTime releaseDate;
    private Boolean isPrivate;
    private UserInfoDto userDto;
    private List<CommentResponseDto> commentDtoList;
    private List<TagResponseDto> tagDtoList;
}
