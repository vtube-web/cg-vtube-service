package com.cgvtube.cgvtubeservice.payload.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private String avatar;
    private String banner;
    private String channelName;
    private String description;
    private Long subscribers;
    private List<Long> videoList;
    private List<Long> likedVideos;
    private List<Long> disLikedVideos;
    private List<Long> subscriptions;
}


