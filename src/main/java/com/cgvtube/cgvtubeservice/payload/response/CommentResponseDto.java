package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long like;
    private Long dislike;
    private Long videoId;
    private String content;
    private List<ReplyResponseDto> replyDtoList;
    private LocalDateTime createAt;
    private UserResponseDto userResponseDto;
}
