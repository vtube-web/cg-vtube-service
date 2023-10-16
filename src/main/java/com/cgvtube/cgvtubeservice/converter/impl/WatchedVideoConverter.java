package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.UserWatchedVideo;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.WatchedVideoDTO;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class WatchedVideoConverter implements Function<Page<UserWatchedVideo>, List<WatchedVideoDTO>> {
    private final VideoRepository videoRepository;

    public List<WatchedVideoDTO> apply(Page<UserWatchedVideo> watchedVideoPage) {
        return watchedVideoPage.map(watchedVideo -> {
            WatchedVideoDTO watchedVideoDTO = new WatchedVideoDTO();
            BeanUtils.copyProperties(watchedVideo, watchedVideoDTO);

            Video video = videoRepository.findById(watchedVideo.getVideo().getId()).orElse(null);
            if (video != null) {
                watchedVideoDTO.setVideoId(video.getId());
                watchedVideoDTO.setTitle(video.getTitle());
                watchedVideoDTO.setDescription(video.getDescription());
                watchedVideoDTO.setVideoUrl(video.getVideoUrl());
                watchedVideoDTO.setCreateAt(video.getCreateAt());
                watchedVideoDTO.setThumbnail(video.getThumbnail());
                watchedVideoDTO.setViews(video.getViews());
                watchedVideoDTO.setUserId(video.getUser().getId());
                watchedVideoDTO.setUserName(video.getUser().getUserName());
            }
            return watchedVideoDTO;
        }).getContent();
    }

}
