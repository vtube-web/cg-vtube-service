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
public class ShortsResponseDto {

    private Long id;
    private Long views;
    private Long likes;
    private Long dislikes;
    private String title;
    private String shortsUrl;
    private LocalDateTime createAt;

    private UserResponseDto userDto;
    private List<CommentShortsResponseDto> commentShortsDtoList;
    private List<TagShortsResponseDto> tagShortsDtoList;
}
