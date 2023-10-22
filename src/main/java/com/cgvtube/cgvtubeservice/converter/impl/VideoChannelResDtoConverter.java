package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.VideoChannelResDto;
import com.cgvtube.cgvtubeservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class VideoChannelResDtoConverter implements Function<Page<Video>, PageResponseDTO<VideoChannelResDto>> {
    private final CommentService commentService;
    @Override
    public PageResponseDTO<VideoChannelResDto> apply(Page<Video> videoPage) {
        PageResponseDTO<VideoChannelResDto> pageResponseDTO = new PageResponseDTO<>();

        List<VideoChannelResDto> videoChannelResDtos = new ArrayList<>();
        for (Video element : videoPage.getContent()){
            VideoChannelResDto channelResDto = new VideoChannelResDto();
            BeanUtils.copyProperties(element,channelResDto);
            channelResDto.setTotalComment(commentService.getTotalCommentByIdVideo(channelResDto.getId()));
            videoChannelResDtos.add(channelResDto);
        }
        System.out.println(videoPage.hasNext());
        System.out.println(videoPage.hasPrevious());
        pageResponseDTO.setContent(videoChannelResDtos);
        pageResponseDTO.setPageSize(videoPage.getSize());
        pageResponseDTO.setTotalPages(videoPage.getTotalPages());
        pageResponseDTO.setHasNext(videoPage.hasNext());
        pageResponseDTO.setHasPrevious(videoPage.hasPrevious());
        pageResponseDTO.setTotalElements(videoPage.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(videoPage.getNumber());
        return pageResponseDTO;
    }
}
