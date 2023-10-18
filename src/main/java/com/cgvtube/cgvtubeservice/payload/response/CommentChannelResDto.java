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
public class CommentChannelResDto {
    private Long id;
    private Long likes;
    private Long dislikes;
    private VideoCommentChannelResDto videoDto;
    private String content;
    private List<ReplyResponseDto> replyDtoList;
    private LocalDateTime createAt;
    private UserResponseDto userResponseDto;
}
