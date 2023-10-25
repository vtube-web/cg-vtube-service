package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Subscription;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.UserLikedVideo;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserInfoConverter implements GeneralConverter<UserInfoDto, User> {
    @Override
    public User convert(UserInfoDto source) {
        return null;
    }

    @Override
    public UserInfoDto revert(User target) {
        return UserInfoDto.builder()
                .id(target.getId())
                .userName(target.getUserName())
                .avatar(target.getAvatar())
                .banner(target.getBanner())
                .channelName(target.getChannelName())
                .email(target.getEmail())
                .createdAt(target.getCreatedAt())
                .description(target.getDescription())
                .subscribers(target.getSubscribers())
                .videoList(getVideoIds(target.getVideoList()))
                .likedVideos(getVideoIds(getVideoLikeIds(target.getLikedVideos(), true)))
                .disLikedVideos(getVideoIds(getVideoLikeIds(target.getLikedVideos(), false)))
                .subscriptions(getChannelId(getUser(target.getSubscriptions())))
                .build();
    }

    @Override
    public List<User> convert(List<UserInfoDto> sources) {
        return null;
    }

    @Override
    public List<UserInfoDto> revert(List<User> targets) {
        List<UserInfoDto> userDtoList = new ArrayList<>();
        for (User target : targets) {
            userDtoList.add(revert(target));
        }
        return userDtoList;
    }

    private List<Long> getVideoIds(List<Video> videos) {
        return videos.stream()
                .map(Video::getId)
                .collect(Collectors.toList());
    }

    private List<Video> getVideoLikeIds(List<UserLikedVideo> videos, boolean likedStatus) {
        return videos.stream()
                .filter(userLikedVideo -> userLikedVideo.isLikedStatus() == likedStatus)
                .map(UserLikedVideo::getVideo)
                .collect(Collectors.toList());
    }

    private List<User> getUser(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(Subscription::getSubscriber)
                .collect(Collectors.toList());
    }

    private List<Long> getChannelId(List<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}
