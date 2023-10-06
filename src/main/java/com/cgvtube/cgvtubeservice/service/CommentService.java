package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentService {
    List<CommentResponseDto> getListCommentDtoByVideo(Video id);

    void save(CommentRequestDto commentRequestDto);

}
