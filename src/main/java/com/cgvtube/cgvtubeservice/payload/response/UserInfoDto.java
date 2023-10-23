package com.cgvtube.cgvtubeservice.payload.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private Long id;
    private String userName;
    private String avatar;
    private List<Long> videoList;
    private List<Long> likedVideos;
    private List<Long> subscriptions;
}


