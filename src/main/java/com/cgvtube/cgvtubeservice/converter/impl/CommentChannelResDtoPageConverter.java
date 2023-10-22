package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.payload.response.CommentChannelResDto;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CommentChannelResDtoPageConverter implements Function<Page<Comment>,PageResponseDTO<CommentChannelResDto>> {

    private final Function<List<Comment>,List<CommentChannelResDto>> pageFunction;
    @Override
    public PageResponseDTO<CommentChannelResDto> apply(Page<Comment> commentPage) {
        PageResponseDTO<CommentChannelResDto> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(pageFunction.apply(commentPage.getContent()));
        pageResponseDTO.setPageSize(commentPage.getSize());
        pageResponseDTO.setTotalPages(commentPage.getTotalPages());
        pageResponseDTO.setHasNext(commentPage.hasNext());
        pageResponseDTO.setHasPrevious(commentPage.hasPrevious());
        pageResponseDTO.setTotalElements(commentPage.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(commentPage.getNumber());
        return pageResponseDTO;
    }
}
