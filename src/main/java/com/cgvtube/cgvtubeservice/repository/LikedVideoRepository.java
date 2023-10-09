package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.LikedVideo;
import com.cgvtube.cgvtubeservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedVideoRepository extends JpaRepository<LikedVideo, Long> {
    Page<LikedVideo> findByUser(User user, Pageable pageableRequest);

}