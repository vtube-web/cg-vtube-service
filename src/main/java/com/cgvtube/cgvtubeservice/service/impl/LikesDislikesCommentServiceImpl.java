package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.UserLikesDislikesComments;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import com.cgvtube.cgvtubeservice.repository.LikesDislikesCommentRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.service.LikesDislikesCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesDislikesCommentServiceImpl implements LikesDislikesCommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikesDislikesCommentRepository likesDislikesCommentRepository;

    @Override
    public ResponseDto userUpLikeComment(Long commentId, UserDetails currentUser) {
        ResponseDto responseDto;
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        UserLikesDislikesComments userLikesComment = likesDislikesCommentRepository.findByUserIdAndCommentId(user.getId(), commentId);
        Comment comment = commentRepository.findById(commentId).orElse(new Comment());
        if (userLikesComment == null) {
            UserLikesDislikesComments userLikesComments = UserLikesDislikesComments
                    .builder()
                    .user(user)
                    .comment(comment)
                    .likes(true)
                    .dislikes(false)
                    .build();
            UserLikesDislikesComments likesComments = likesDislikesCommentRepository.save(userLikesComments);
            responseDto = userReturnResponseDto(likesComments, "up likes success", "up likes error");
        } else {
            if (userLikesComment.getLikes()) {
                userLikesComment.setLikes(false);
                userLikesComment.setDislikes(false);
                UserLikesDislikesComments likesComments = likesDislikesCommentRepository.save(userLikesComment);
                responseDto = userReturnResponseDto(likesComments, "down likes success", "down likes error");
            } else {
                userLikesComment.setLikes(true);
                userLikesComment.setDislikes(false);
                UserLikesDislikesComments likesComments = likesDislikesCommentRepository.save(userLikesComment);
                responseDto = userReturnResponseDto(likesComments, "up likes success", "up likes error");
            }

        }
        totalLikeAndDislike(comment);
        return responseDto;
    }


    @Override
    public ResponseDto userDislikeComment(Long commentId, UserDetails currentUser) {
        ResponseDto responseDto;
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        UserLikesDislikesComments userLikesComment = likesDislikesCommentRepository.findByUserIdAndCommentId(user.getId(), commentId);
        Comment comment = commentRepository.findById(commentId).orElse(new Comment());
        if (userLikesComment == null) {
            UserLikesDislikesComments userDislikesComments = UserLikesDislikesComments
                    .builder()
                    .user(user)
                    .comment(comment)
                    .likes(true)
                    .dislikes(false)
                    .build();
            UserLikesDislikesComments dislikesComments = likesDislikesCommentRepository.save(userDislikesComments);
            responseDto = userReturnResponseDto(dislikesComments, "up dislike success", "up dislike error");
        } else {
            if (userLikesComment.getDislikes()) {
                userLikesComment.setDislikes(false);
                userLikesComment.setLikes(false);
                UserLikesDislikesComments dislikesComments = likesDislikesCommentRepository.save(userLikesComment);
                responseDto = userReturnResponseDto(dislikesComments, "down dislike success", "down dislike error");
            } else {
                userLikesComment.setDislikes(true);
                userLikesComment.setLikes(false);
                UserLikesDislikesComments dislikesComments = likesDislikesCommentRepository.save(userLikesComment);
                responseDto = userReturnResponseDto(dislikesComments, "up dislike success", "up dislike error");
            }

        }
        totalLikeAndDislike(comment);
        return responseDto;
    }

    private void totalLikeAndDislike(Comment comment) {
        long countLikes = 0;
        long countDislikes = 0;
        for (UserLikesDislikesComments element : comment.getUserLikesDislikes()) {
            if (element.getLikes()) {
                countLikes += 1;
            }
            if (element.getDislikes()) {
                countDislikes += 1;
            }
        }
        comment.setLikes(countLikes);
        comment.setDislikes(countDislikes);
        commentRepository.save(comment);
    }

    private static ResponseDto userReturnResponseDto(UserLikesDislikesComments likesDislikesComments, String success, String error) {
        ResponseDto responseDto;
        if (likesDislikesComments != null) {
            responseDto = ResponseDto
                    .builder()
                    .message(success)
                    .status("200")
                    .data(true)
                    .build();
        } else {
            responseDto = ResponseDto
                    .builder()
                    .message(error)
                    .status("400")
                    .data(false)
                    .build();
        }
        return responseDto;
    }


}
