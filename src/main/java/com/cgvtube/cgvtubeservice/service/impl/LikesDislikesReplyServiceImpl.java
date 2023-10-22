package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.entity.Reply;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.UserLikesDislikesReply;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.LikesDislikesReplyRepository;
import com.cgvtube.cgvtubeservice.repository.ReplyRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.service.LikesDislikesReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesDislikesReplyServiceImpl implements LikesDislikesReplyService {
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final LikesDislikesReplyRepository likesDislikesReplyRepository;

    @Override
    public ResponseDto userUpLikeReply(Long replyId, UserDetails currentUser) {
        ResponseDto responseDto;
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        UserLikesDislikesReply userLikesReply = likesDislikesReplyRepository.findByUserIdAndReplyId(user.getId(), replyId);
        Reply reply = replyRepository.findById(replyId).orElse(new Reply());
        if (userLikesReply == null) {
            UserLikesDislikesReply userLikesReplies = UserLikesDislikesReply
                    .builder()
                    .user(user)
                    .reply(reply)
                    .likes(true)
                    .dislikes(false)
                    .build();
            UserLikesDislikesReply likesReply = likesDislikesReplyRepository.save(userLikesReplies);
            responseDto = userReturnResponseDto(likesReply, "Up like success", "Up like error");
        } else {
            if (userLikesReply.getLikes()) {
                userLikesReply.setLikes(false);
                userLikesReply.setDislikes(false);
                UserLikesDislikesReply likesComments = likesDislikesReplyRepository.save(userLikesReply);
                responseDto = userReturnResponseDto(likesComments, "Down like success", "Down like error");
            } else {
                userLikesReply.setLikes(true);
                userLikesReply.setDislikes(false);
                UserLikesDislikesReply likesComments = likesDislikesReplyRepository.save(userLikesReply);
                responseDto = userReturnResponseDto(likesComments, "Up like success", "Up like error");
            }

        }
        totalLikeAndDislike(reply);
        return responseDto;
    }

    @Override
    public ResponseDto userDislikeReply(Long replyId, UserDetails currentUser) {
        ResponseDto responseDto;
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        UserLikesDislikesReply userLikesReply = likesDislikesReplyRepository.findByUserIdAndReplyId(user.getId(), replyId);
        Reply reply = replyRepository.findById(replyId).orElse(new Reply());
        if (userLikesReply == null) {
            UserLikesDislikesReply userLikesReplies = UserLikesDislikesReply
                    .builder()
                    .user(user)
                    .reply(reply)
                    .likes(false)
                    .dislikes(true)
                    .build();
            UserLikesDislikesReply likesReply = likesDislikesReplyRepository.save(userLikesReplies);
            responseDto = userReturnResponseDto(likesReply, "Up dislike success", "Up dislike error");
        } else {
            if (userLikesReply.getDislikes()) {
                userLikesReply.setDislikes(false);
                userLikesReply.setLikes(false);
                UserLikesDislikesReply likesComments = likesDislikesReplyRepository.save(userLikesReply);
                responseDto = userReturnResponseDto(likesComments, "Down dislike success", "Down dislike error");
            } else {
                userLikesReply.setDislikes(true);
                userLikesReply.setLikes(false);
                UserLikesDislikesReply likesComments = likesDislikesReplyRepository.save(userLikesReply);
                responseDto = userReturnResponseDto(likesComments, "Up dislike success", "Up dislike error");
            }

        }
        totalLikeAndDislike(reply);
        return responseDto;
    }

    private void totalLikeAndDislike(Reply reply) {
        long countLikes = 0;
        long countDislikes = 0;
        for (UserLikesDislikesReply element : reply.getUserLikesDislikes()) {
            if (element.getLikes()) {
                countLikes += 1;
            }
            if (element.getDislikes()) {
                countDislikes += 1;
            }
        }
        reply.setLikes(countLikes);
        reply.setDislikes(countDislikes);
        replyRepository.save(reply);
    }

    private static ResponseDto userReturnResponseDto(UserLikesDislikesReply likesDislikesReply, String success, String error) {
        ResponseDto responseDto;
        if (likesDislikesReply != null) {
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
