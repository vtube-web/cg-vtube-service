package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.CommentResponseConverter;
import com.cgvtube.cgvtubeservice.converter.impl.CommentShortsResponseConverter;
import com.cgvtube.cgvtubeservice.converter.impl.ShortsResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.*;
import com.cgvtube.cgvtubeservice.entity.CommentShorts;
import com.cgvtube.cgvtubeservice.entity.Shorts;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.CommentShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.CommentShortsResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import com.cgvtube.cgvtubeservice.repository.CommentShortsRepository;
import com.cgvtube.cgvtubeservice.repository.ShortsRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.CommentService;
import com.cgvtube.cgvtubeservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentResponseConverter commentResponseConverter;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Function<List<Comment>,List<CommentChannelResDto>> pageFunction;
    private final CommentShortsRepository commentShortsRepository;
    private final CommentShortsResponseConverter commentShortsResponseConverter;
    private final ShortsRepository shortsRepository;

    @Override
    public List<CommentResponseDto> getListCommentDtoByVideo(Video video) {
        List<Comment> commentList = commentRepository.findAllByVideo(video);
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
    public List<CommentShortsResponseDto> getListCommentDtoByShorts(Shorts shorts) {
        List<CommentShorts> commentShortsList = commentShortsRepository.findAllByShorts(shorts);
        return commentShortsResponseConverter.convert(commentShortsList);
    }

    @Override
    public ResponseDto save(Long shortsId, CommentShortsRequestDto commentShortsRequestDto) {
        CommentShorts commentShorts = CommentShorts.builder()
                .user(userRepository.findById(userService.getCurrentUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found")))
                .content(commentShortsRequestDto.getContent())
                .likes(0L)
                .dislikes(0L)
                .shorts(shortsRepository.findById(shortsId)
                        .orElseThrow(() -> new EntityNotFoundException("Video not found")))
                .createAt(LocalDateTime.now())
                .build();
        commentShortsRepository.save(commentShorts);
        return ResponseDto.builder()
                .message("Success to save comment ")
                .status("200")
                .data(true)
                .build();
    }
    public Long getTotalCommentByIdVideo(Long id) {
        List<Comment> commentList = commentRepository.findAllByVideoId(id);
        return (long) commentList.size();
    }

    @Override
    public ResponseDto getCommentByChannel(Pageable pageable, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageableNew = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Comment> commentPage = commentRepository.findAllByUserId(pageableNew,user.getId());
        PageResponseDTO<CommentChannelResDto> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(pageFunction.apply(commentPage.getContent()));
        pageResponseDTO.setPageSize(commentPage.getSize());
        pageResponseDTO.setTotalPages(commentPage.getTotalPages());
        pageResponseDTO.setHasNext(commentPage.hasNext());
        pageResponseDTO.setHasPrevious(commentPage.hasPrevious());
        pageResponseDTO.setTotalElements(commentPage.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(commentPage.getNumber());
        ResponseDto responseDto = ResponseDto.builder().message("get list comment success").status("200").data(pageResponseDTO).build();
        return responseDto;
    }
}
