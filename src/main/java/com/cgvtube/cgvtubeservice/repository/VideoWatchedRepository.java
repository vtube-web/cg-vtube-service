package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.UserWatchedVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoWatchedRepository extends JpaRepository<UserWatchedVideo, Long> {
    Page<UserWatchedVideo> findByUser(User user, Pageable pageableRequest);

    int deleteByUserIdAndVideoId(Long userId, Long videoId);

    int deleteByUserId(Long userId);

}
