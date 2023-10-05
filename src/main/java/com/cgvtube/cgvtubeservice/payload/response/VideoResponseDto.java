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
    private Long like;
    private Long dislike;
    private String title;
    private String description;
    private String videoUrl;
    private LocalDateTime createAt;

    private UserResponseDto userDto;
    private List<CommentResponseDto> commentDtoList;
    private List<TagResponseDto> tagDtoList;
}
