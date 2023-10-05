package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.Converter;
import com.cgvtube.cgvtubeservice.entity.Reply;
import com.cgvtube.cgvtubeservice.payload.response.CommentResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ReplyConverter implements Converter<Reply,ReplyResponseDto> {
    private final UserResponseDtoConverter userResponseDtoConverter;
    @Override
    public ReplyResponseDto convert(Reply source) {
        ReplyResponseDto target = new ReplyResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setUserResponseDto(userResponseDtoConverter.revert(source.getUser()));
        return target;
    }

    @Override
    public Reply revert(ReplyResponseDto target) {
        return null;
    }

    @Override
    public List<ReplyResponseDto> convert(List<Reply> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Reply> revert(List<ReplyResponseDto> targets) {
        return null;
    }
}
