package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.payload.response.CommentChannelResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CommentChannelResDtoConverter implements Function<List<Comment>,List<CommentChannelResDto>> {
    private final ReplyResponseConverter replyResponseConverter;
    private final UserResponseConverter userResponseConverter;
    private final VideoCommentChannelResDtoConverter videoCommentChannelResDtoConverter;
    @Override
    public List<CommentChannelResDto> apply(List<Comment> comments) {
        List<CommentChannelResDto> commentChannelResDtoList = new ArrayList<>();
        for (Comment element : comments){
            CommentChannelResDto commentChannelResDto = new CommentChannelResDto();
            BeanUtils.copyProperties(element,commentChannelResDto);
            commentChannelResDto.setVideoDto(videoCommentChannelResDtoConverter.apply(element.getVideo()));
            commentChannelResDto.setReplyDtoList(replyResponseConverter.convert(element.getReplyList()));
            commentChannelResDto.setUserResponseDto(userResponseConverter.revert(element.getUser()));
            commentChannelResDtoList.add(commentChannelResDto);
        }
        return commentChannelResDtoList;
    }
}
