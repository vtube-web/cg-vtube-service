package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.WatchedVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoWatchedRepository extends JpaRepository<WatchedVideo, Long> {
    Page<WatchedVideo> findByUser(User user, Pageable pageableRequest);

    int deleteByUserIdAndVideoId(Long userId, Long videoId);

    int deleteByUserId(Long userId);

}
