package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Reply;
import com.cgvtube.cgvtubeservice.payload.response.ReplyResponseDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ReplyResponseConverter implements GeneralConverter<Reply, ReplyResponseDto> {
    private final UserResponseConverter userResponseDtoConverter;
    private final CommentRepository commentRepository;
    @Override
    public ReplyResponseDto convert(Reply source) {
        ReplyResponseDto target = new ReplyResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setUserResponseDto(userResponseDtoConverter.revert(source.getUser()));
        target.setCommentId(source.getComment().getId());
        return target;
    }

    @Override
    public Reply revert(ReplyResponseDto target) {
        Reply source = new Reply();
        BeanUtils.copyProperties(target, source);
        source.setUser(userResponseDtoConverter.convert(target.getUserResponseDto()));
        source.setComment(commentRepository.findById(target.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment not foundwith ID: " + target.getCommentId()
                )));
        return source;
    }

    @Override
    public List<ReplyResponseDto> convert(List<Reply> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Reply> revert(List<ReplyResponseDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
