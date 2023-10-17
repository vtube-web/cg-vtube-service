package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.CommentResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.CommentService;
import com.cgvtube.cgvtubeservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentResponseConverter commentResponseConverter;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public List<CommentResponseDto> getListCommentDtoByVideo(Video video) {
        List<com.cgvtube.cgvtubeservice.entity.Comment> commentList = commentRepository.findAllByVideo(video);
        return commentResponseConverter.convert(commentList);
    }

    @Override
    public ResponseDto save(Long videoId, CommentRequestDto commentRequestDto) {
        Comment comment = Comment.builder()
                .user(userRepository.findById(userService.getCurrentUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found")))
                .content(commentRequestDto.getContent())
                .likes(0L)
                .dislikes(0L)
                .video(videoRepository.findById(videoId)
                        .orElseThrow(() -> new EntityNotFoundException("Video not found")))
                .createAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
        return ResponseDto.builder()
                .message("Success to save comment ")
                .status("200")
                .data(true)
                .build();
    }

    @Override
    public ResponseDto delete(Long commentId) {
        commentRepository.deleteById(commentId);
        return ResponseDto.builder()
                .message("Success")
                .status("200")
                .data(true)
                .build();
    }

    @Override
    public Long getTotalCommentByIdVideo(Long id) {
        List<Comment> commentList = commentRepository.findAllByVideoId(id);
        return (long) commentList.size();
    }
}
