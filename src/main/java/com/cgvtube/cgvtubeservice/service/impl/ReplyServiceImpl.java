package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.entity.Reply;
import com.cgvtube.cgvtubeservice.entity.ReplyShorts;
import com.cgvtube.cgvtubeservice.payload.request.ReplyRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.ReplyShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import com.cgvtube.cgvtubeservice.repository.CommentShortsRepository;
import com.cgvtube.cgvtubeservice.repository.ReplyRepository;
import com.cgvtube.cgvtubeservice.repository.ReplyShortsRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.service.ReplyService;
import com.cgvtube.cgvtubeservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    private final CommentShortsRepository commentShortsRepository;
    private final ReplyShortsRepository replyShortsRepository;

    @Override
    public ResponseDto save(Long commentId, ReplyRequestDto replyRequestDto) {
        Reply reply = Reply.builder()
                .user(userRepository.findById(userService.getCurrentUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found")))
                .comment(commentRepository.findById(commentId)
                        .orElseThrow(() -> new EntityNotFoundException("Comment not found")))
                .content(replyRequestDto.getContent())
                .likes(0L)
                .dislikes(0L)
                .createAt(LocalDateTime.now())
                .build();
        replyRepository.save(reply);
        return ResponseDto.builder()
                .message("Success to save reply")
                .status("200")
                .data(true)
                .build();
    }

    @Override
    public ResponseDto save(Long shortsId, ReplyShortsRequestDto replyShortsRequestDto) {
        ReplyShorts replyShorts = ReplyShorts.builder()
                .user(userRepository.findById(userService.getCurrentUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found")))
                .commentShorts(commentShortsRepository.findById(shortsId)
                        .orElseThrow(() -> new EntityNotFoundException("Comment not found")))
                .content(replyShortsRequestDto.getContent())
                .likes(0L)
                .dislikes(0L)
                .createAt(LocalDateTime.now())
                .build();
        replyShortsRepository.save(replyShorts);
        return ResponseDto.builder()
                .message("Success to save reply")
                .status("200")
                .data(true)
                .build();
    }
}
