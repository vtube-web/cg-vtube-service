package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.ReplyShorts;
import com.cgvtube.cgvtubeservice.payload.response.CommentShortsResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ReplyShortsResponseDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import com.cgvtube.cgvtubeservice.repository.CommentShortsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ReplyShortsResponseConverter implements GeneralConverter<ReplyShorts, ReplyShortsResponseDto> {
    private final UserResponseConverter userResponseDtoConverter;
    private final CommentShortsRepository commentShortsRepository;
    @Override
    public ReplyShortsResponseDto convert(ReplyShorts source) {
        ReplyShortsResponseDto target = new ReplyShortsResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setUserResponseDto(userResponseDtoConverter.revert(source.getUser()));
        target.setCommentShortsId(source.getCommentShorts().getId());
        return target;
    }

    @Override
    public ReplyShorts revert(ReplyShortsResponseDto target) {
        ReplyShorts source = new ReplyShorts();
        BeanUtils.copyProperties(target, source);
        source.setUser(userResponseDtoConverter.convert(target.getUserResponseDto()));
        source.setCommentShorts(commentShortsRepository.findById(target.getCommentShortsId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment not foundwith ID: " + target.getCommentShortsId()
                )));
        return source;
    }

    @Override
    public List<ReplyShortsResponseDto> convert(List<ReplyShorts> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<ReplyShorts> revert(List<ReplyShortsResponseDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
