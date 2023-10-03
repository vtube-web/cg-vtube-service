package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.Converter;
import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentConverter implements Converter<Comment, CommentResponseDto> {
    private final UserResponseDtoConverter userResponseDtoConverter;

    @Override
    public CommentResponseDto convert(Comment source) {
        CommentResponseDto target = new CommentResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setParentCommentId(source.getParentComment().getId());
        target.setUserResponseDto(userResponseDtoConverter.revert(source.getUser()));
        return target;
    }

    @Override
    public Comment revert(CommentResponseDto target) {
        return null;
    }

    @Override
    public List<CommentResponseDto> convert(List<Comment> sources) {
        return sources.stream().map(this::convert).toList();

    }

    @Override
    public List<Comment> revert(List<CommentResponseDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
