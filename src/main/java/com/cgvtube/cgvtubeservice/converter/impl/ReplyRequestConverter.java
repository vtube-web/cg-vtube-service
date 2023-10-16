package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Reply;
import com.cgvtube.cgvtubeservice.payload.request.ReplyRequestDto;
import com.cgvtube.cgvtubeservice.repository.CommentRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyRequestConverter implements GeneralConverter<Reply, ReplyRequestDto> {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public ReplyRequestDto convert(Reply source) {
        ReplyRequestDto target = new ReplyRequestDto();
        target.setContent(source.getContent());
        target.setUserId(source.getUser().getId());
        target.setCommentId(source.getComment().getId());
        return target;
    }

    @Override
    public Reply revert(ReplyRequestDto target) {
        Reply source = new Reply();
        source.setContent(target.getContent());
        source.setUser(userRepository.findById(target.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        source.setComment(commentRepository.findById(target.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Video not found")));
        return source;
    }

    @Override
    public List<ReplyRequestDto> convert(List<Reply> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Reply> revert(List<ReplyRequestDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
