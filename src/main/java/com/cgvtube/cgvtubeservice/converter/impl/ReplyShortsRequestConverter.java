package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.ReplyShorts;
import com.cgvtube.cgvtubeservice.payload.request.ReplyShortsRequestDto;
import com.cgvtube.cgvtubeservice.repository.CommentShortsRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyShortsRequestConverter implements GeneralConverter<ReplyShorts, ReplyShortsRequestDto> {
    private final UserRepository userRepository;
    private final CommentShortsRepository commentShortsRepository;
    @Override
    public ReplyShortsRequestDto convert(ReplyShorts source) {
        ReplyShortsRequestDto target = new ReplyShortsRequestDto();
        target.setContent(source.getContent());
        target.setUserId(source.getUser().getId());
        target.setCommentShortsId(source.getCommentShorts().getId());
        return target;
    }

    @Override
    public ReplyShorts revert(ReplyShortsRequestDto target) {
        ReplyShorts source = new ReplyShorts();
        source.setContent(target.getContent());
        source.setUser(userRepository.findById(target.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        source.setCommentShorts(commentShortsRepository.findById(target.getCommentShortsId())
                .orElseThrow(() -> new EntityNotFoundException("Video not found")));
        return source;
    }

    @Override
    public List<ReplyShortsRequestDto> convert(List<ReplyShorts> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<ReplyShorts> revert(List<ReplyShortsRequestDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
