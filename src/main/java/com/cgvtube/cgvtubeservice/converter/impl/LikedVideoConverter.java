package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.UserLikedVideo;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.LikedVideoDTO;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LikedVideoConverter implements Function<Page<UserLikedVideo>, List<LikedVideoDTO>> {
    private final VideoRepository videoRepository;

    public List<LikedVideoDTO> apply(Page<UserLikedVideo> likedVideos) {
        return likedVideos.map(likedVideo -> {
            LikedVideoDTO likedVideoDTO = new LikedVideoDTO();
            BeanUtils.copyProperties(likedVideos, likedVideoDTO);
            Video video = videoRepository.findById(likedVideo.getVideo().getId()).orElse(new Video());
            if (video != null) {
                likedVideoDTO.setVideoId(video.getId());
                likedVideoDTO.setTitle(video.getTitle());
                likedVideoDTO.setDescription(video.getDescription());
                likedVideoDTO.setVideoUrl(video.getVideoUrl());
                likedVideoDTO.setThumbnail(video.getThumbnail());
                likedVideoDTO.setViews(video.getViews());
                likedVideoDTO.setDuration(video.getDuration());
                likedVideoDTO.setIsShorts(video.getIsShorts());
                likedVideoDTO.setLikedAt(likedVideo.getLikedAt());
                likedVideoDTO.setCreateAt(video.getCreateAt());
                likedVideoDTO.setUserId(video.getUser().getId());
                likedVideoDTO.setUserName(video.getUser().getUserName());
                likedVideoDTO.setUserLike(likedVideo.getUser().getUserName());
            }
            return likedVideoDTO;
        }).getContent();
    }

}
