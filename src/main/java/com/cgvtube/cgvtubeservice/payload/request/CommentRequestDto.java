package com.cgvtube.cgvtubeservice.payload.request;

import com.cgvtube.cgvtubeservice.payload.response.ReplyResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private Long videoId;
    private Long userId;
    private Long likes;
    private Long dislikes;
    private String content;
    private LocalDateTime createAt;
    private List<ReplyResponseDto> replyResponseDtoList;
}
