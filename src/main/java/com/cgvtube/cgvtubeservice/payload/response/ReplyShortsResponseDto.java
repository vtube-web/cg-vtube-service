package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyShortsResponseDto {
    private Long id;
    private Long likes;
    private Long dislikes;
    private String content;
    private LocalDateTime createAt;
    private UserResponseDto userResponseDto;
    private Long commentShortsId;
}
