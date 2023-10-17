package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.ContentCommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentService {
    List<CommentResponseDto> getListCommentDtoByVideo(Video id);
    ResponseDto save(Long videoId, CommentRequestDto commentRequestDto);
    ResponseDto delete(Long commentId);

    Long getTotalCommentByIdVideo(Long id);
}
