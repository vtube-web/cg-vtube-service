package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.CommentRequestConverter;
import com.cgvtube.cgvtubeservice.converter.impl.CommentResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import com.cgvtube.cgvtubeservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentResponseConverter commentResponseConverter;
    private final CommentRequestConverter commentRequestConverter;

    @Override
    public List<CommentResponseDto> getListCommentDtoByVideo(Video video) {
        List<Comment> commentList = commentRepository.findAllByVideo(video);
        return commentResponseConverter.convert(commentList);
    }

    @Override
    public void save(CommentRequestDto commentRequestDto) {
        commentRepository.save(commentRequestConverter.revert(commentRequestDto));
    }
}
