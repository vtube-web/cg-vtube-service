package com.cgvtube.cgvtubeservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEditProfileReqDto {
    private Long id;
    private String userName;
    private String channelName;
    private String description;
}
