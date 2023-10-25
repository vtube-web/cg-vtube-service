package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.CommentShorts;
import com.cgvtube.cgvtubeservice.entity.ReplyShorts;
import com.cgvtube.cgvtubeservice.payload.response.CommentShortsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class CommentShortsResponseConverter implements GeneralConverter<CommentShorts, CommentShortsResponseDto> {

    private final UserResponseConverter userResponseConverter;
    private final ReplyShortsResponseConverter replyShortsResponseConverter;
    @Override
    public CommentShortsResponseDto convert(CommentShorts source) {
        CommentShortsResponseDto target = new CommentShortsResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setVideoId(source.getVideo().getId());
        target.setCreateAt(source.getCreateAt());
        target.setUserResponseDto(userResponseConverter.revert(source.getUser()));
        target.setReplyShortsDtoList(replyShortsResponseConverter.convert(source.getReplyShortsList()));
        return target;
    }

    @Override
    public CommentShorts revert(CommentShortsResponseDto target) {
        return null;
    }

    @Override
    public List<CommentShortsResponseDto> convert(List<CommentShorts> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<CommentShorts> revert(List<CommentShortsResponseDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
