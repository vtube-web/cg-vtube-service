package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.UserLikedVideo;
import com.cgvtube.cgvtubeservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedVideoRepository extends JpaRepository<UserLikedVideo, Long> {
    Page<UserLikedVideo> findByUser(User user, Pageable pageableRequest);

    int deleteByUserIdAndVideoId(Long userId, Long videoId);

//    Boolean existsByUserIdAndVideoId(Long id, Long videoId);

//    Boolean findLikedStatusByUserIdAndVideoId(Long id, Long videoId);

    UserLikedVideo findByUserIdAndVideoId(Long id, Long videoId);

//    void updateLikedStatus(Long userId, Long videoId, boolean likedStatus);

}
