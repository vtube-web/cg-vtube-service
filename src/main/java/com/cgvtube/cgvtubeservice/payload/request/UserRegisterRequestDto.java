package com.cgvtube.cgvtubeservice.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserRegisterRequestDto {
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,16}$"
            , message = "Must contain uppercase, lowercase, number, symbol and be between 8 - 16 characters")
    @NotBlank
    private String password;

    @NotBlank
    private String userName;

//    @NotBlank
    private String channelName;


}
