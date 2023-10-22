package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.comparator.ComparatorReplyCreateAt;
import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.entity.UserLikesDislikesComments;
import com.cgvtube.cgvtubeservice.payload.response.CommentChannelResDto;
import com.cgvtube.cgvtubeservice.payload.response.LikesDislikesCommentsResDto;
import com.cgvtube.cgvtubeservice.payload.response.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CommentChannelResDtoConverter implements Function<List<Comment>, List<CommentChannelResDto>> {
    private final ReplyResponseConverter replyResponseConverter;
    private final UserResponseConverter userResponseConverter;
    private final VideoCommentChannelResDtoConverter videoCommentChannelResDtoConverter;
    private final Function <List<UserLikesDislikesComments>,LikesDislikesCommentsResDto> voteFunctionConverter;
    @Override
    public List<CommentChannelResDto> apply(List<Comment> comments) {
        List<CommentChannelResDto> commentChannelResDtoList = new ArrayList<>();
        ComparatorReplyCreateAt comparatorReplyCreateAt = new ComparatorReplyCreateAt();
        for (Comment element : comments) {
            CommentChannelResDto commentChannelResDto = new CommentChannelResDto();
            BeanUtils.copyProperties(element, commentChannelResDto);
            commentChannelResDto.setVideoDto(videoCommentChannelResDtoConverter.apply(element.getVideo()));
            List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>(replyResponseConverter.convert(element.getReplyList()));
            Collections.sort(replyResponseDtoList, comparatorReplyCreateAt);
            commentChannelResDto.setReplyDtoList(replyResponseDtoList);
            commentChannelResDto.setUserResponseDto(userResponseConverter.revert(element.getUser()));
            commentChannelResDto.setUserVoteCommentDto(voteFunctionConverter.apply(element.getUserLikesDislikes()));
            commentChannelResDtoList.add(commentChannelResDto);
        }
        return commentChannelResDtoList;
    }
}
