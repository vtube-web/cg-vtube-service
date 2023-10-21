package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikesDislikesCommentsResDto {
    private Long id;
    private Boolean likes;
    private Boolean dislikes;
    private Long userId;
}
