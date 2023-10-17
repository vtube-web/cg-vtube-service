package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entity.Shorts;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.CommentShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentShortsResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentService {
    List<CommentResponseDto> getListCommentDtoByVideo(Video id);
    ResponseDto save(Long videoId, CommentRequestDto commentRequestDto);
    ResponseDto delete(Long commentId);


    List<CommentShortsResponseDto> getListCommentDtoByShorts(Shorts id);
    ResponseDto save(Long shortsId, CommentShortsRequestDto commentShortsRequestDto);

}
