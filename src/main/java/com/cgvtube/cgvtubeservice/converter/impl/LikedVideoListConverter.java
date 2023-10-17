package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.UserLikedVideo;
import com.cgvtube.cgvtubeservice.payload.response.LikedVideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikedVideoListConverter implements GeneralConverter<LikedVideoDTO, UserLikedVideo> {
    @Override
    public UserLikedVideo convert(LikedVideoDTO source) {
        return null;
    }

    @Override
    public LikedVideoDTO revert(UserLikedVideo target) {
        return LikedVideoDTO.builder()
                .videoId(target.getVideo().getId())
                .title(target.getVideo().getTitle())
                .description(target.getVideo().getDescription())
                .videoUrl(target.getVideo().getVideoUrl())
                .thumbnail(target.getVideo().getThumbnail())
                .views(target.getVideo().getViews())
                .likedAt(target.getLikedAt())
                .createAt(target.getVideo().getCreateAt())
                .userId(target.getUser().getId())
                .userName(target.getUser().getUserName())
                .build();
    }

    @Override
    public List<UserLikedVideo> convert(List<LikedVideoDTO> sources) {
        return null;
    }

    @Override
    public List<LikedVideoDTO> revert(List<UserLikedVideo> targets) {
        return null;
    }
}
