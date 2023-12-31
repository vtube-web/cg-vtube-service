package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.CommentShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.ContentCommentReqDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentShortsResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentService {
    List<CommentResponseDto> getListCommentDtoByVideo(Video id);
    ResponseDto save(Long videoId, CommentRequestDto commentRequestDto);
    ResponseDto delete(Long commentId);


    List<CommentShortsResponseDto> getListCommentDtoByShorts(Video id);
    ResponseDto save(Long videoId, CommentShortsRequestDto commentShortsRequestDto);

    Long getTotalCommentByIdVideo(Long id);

    ResponseDto getCommentByChannel(Pageable pageable,String content, UserDetails currentUser);

    ResponseDto editContentOfCommentByUser(ContentCommentReqDto contentCommentReqDto);
}
