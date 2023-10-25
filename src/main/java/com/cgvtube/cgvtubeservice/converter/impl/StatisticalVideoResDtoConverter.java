package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.StatisticalVideoResDto;
import com.cgvtube.cgvtubeservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class StatisticalVideoResDtoConverter implements Function<List<Video>,StatisticalVideoResDto> {

    @Override
    public StatisticalVideoResDto apply(List<Video> videoList) {
        Long likes=0L;
        Long dislikes=0L;
        Long views=0L;
        for (Video element : videoList){
            likes+=element.getLikes();
            dislikes+= element.getDislikes();
            views += element.getViews();
        }
        return StatisticalVideoResDto.builder().likes(likes).dislikes(dislikes).views(views).build();
    }
}
