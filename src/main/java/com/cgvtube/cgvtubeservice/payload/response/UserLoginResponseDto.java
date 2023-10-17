package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserLoginResponseDto {
    private Long id;
    private String name;
    private String userName;
    private String avatar;
    private String email;
    private String accessToken;
    private String refreshToken;
}
