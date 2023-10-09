package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long like;
    private Long dislike;
    private Long parentCommentId;
    private Long videoId;

    private String content;
    private LocalDate createAt;

    private UserResponseDto userResponseDto;
}
