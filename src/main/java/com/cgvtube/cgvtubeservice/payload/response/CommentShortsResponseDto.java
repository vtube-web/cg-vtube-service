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
public class CommentShortsResponseDto {
    private Long id;
    private Long likes;
    private Long dislikes;
    private Long shortsId;
    private String content;
    private List<ReplyShortsResponseDto> replyShortsDtoList;
    private LocalDateTime createAt;
    private UserResponseDto userResponseDto;
}
