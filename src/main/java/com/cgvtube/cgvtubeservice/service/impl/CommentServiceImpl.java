package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.comparator.ComparatorCommentCreateAt;
import com.cgvtube.cgvtubeservice.converter.impl.CommentResponseConverter;
import com.cgvtube.cgvtubeservice.converter.impl.CommentShortsResponseConverter;
import com.cgvtube.cgvtubeservice.entity.*;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.CommentShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.ContentCommentReqDto;
import com.cgvtube.cgvtubeservice.payload.response.*;
import com.cgvtube.cgvtubeservice.repository.*;
import com.cgvtube.cgvtubeservice.service.CommentService;
import com.cgvtube.cgvtubeservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentResponseConverter commentResponseConverter;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommentShortsRepository commentShortsRepository;
    private final CommentShortsResponseConverter commentShortsResponseConverter;
    private final ShortsRepository shortsRepository;
    private final Function<Page<Comment>,PageResponseDTO<CommentChannelResDto>> pageResponseDTOFunction;
    private final ReplyRepository replyRepository;
    private final LikesDislikesCommentRepository likesDislikesCommentRepository;
    private final LikesDislikesReplyRepository likesDislikesReplyRepository;

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
        List<Reply> replyList = replyRepository.findAllByCommentId(commentId);
        for(Reply element: replyList){
            likesDislikesReplyRepository.deleteAllByReplyId(element.getId());
        }
        replyRepository.deleteAllByCommentId(commentId);
        likesDislikesCommentRepository.deleteAllByCommentId(commentId);
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
    public ResponseDto getCommentByChannel(Pageable pageable,String content, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        List<Video> videos = user.getVideoList();
        Page<Comment> commentPage = getPageCommentsByChannel(pageable,content, videos);
        ResponseDto responseDto = ResponseDto.builder().message("get list comment success").status("200").data(pageResponseDTOFunction.apply(commentPage)).build();
        return responseDto;
    }

    @Override
    public ResponseDto editContentOfCommentByUser(ContentCommentReqDto contentCommentReqDto) {
        Comment comment = commentRepository.findById(contentCommentReqDto.getId()).orElse(new Comment());
        comment.setContent(contentCommentReqDto.getContent());
        commentRepository.save(comment);
        ResponseDto responseDto = ResponseDto.builder().message("get list comment success").status("200").data(true).build();
        return responseDto;
    }

    private Page<Comment> getPageCommentsByChannel(Pageable pageable,String content, List<Video> videos) {
        List<Comment> allComments = new ArrayList<>();
        if(content.equals("")){
            for (Video video : videos) {
                if(video.getIsShorts() == false){
                    List<Comment> comments = commentRepository.findAllByVideoId(video.getId());
                    allComments.addAll(comments);
                }
            }
        }else {
            String contentLike = "%".concat(content).concat("%");
            for (Video video : videos) {
                if(video.getIsShorts() == false) {
                    List<Comment> comments = commentRepository.findAllByVideoIdAndContentLike(video.getId(), contentLike);
                    allComments.addAll(comments);
                }
            }
        }

        ComparatorCommentCreateAt comparatorCommentCreateAt = new ComparatorCommentCreateAt();
        Collections.sort(allComments, comparatorCommentCreateAt);

        int totalElements = allComments.size();
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), totalElements);
        List<Comment> pageContent = allComments.subList(fromIndex, toIndex);
        Page<Comment> commentPage = new PageImpl<>(pageContent, pageable, totalElements);
        return commentPage;
    }
}
