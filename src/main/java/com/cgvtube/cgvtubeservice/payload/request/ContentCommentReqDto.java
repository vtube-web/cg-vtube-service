package com.cgvtube.cgvtubeservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentCommentReqDto {
    private Long id;
    private String content;
}
