package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("SELECT v FROM Video v WHERE v.user.id IN :channelIds")
    Page<Video> findVideosByChannelIds(List<Long> channelIds, Pageable pageableRequest);
    List<Video> findAll();
    Video save(Video video);
    Page<Video> findAllByUserId(Pageable pageable, long id);
    List<Video> findVideosByUserId(Long userId);
    Page<Video> findAllByUserIdAndIsPrivate(Pageable pageable, long id,boolean status);
    Page<Video> findAllByUserIdAndIsShorts(Pageable pageable, long id,Boolean isShort);
    Page<Video> findAllByUserIdAndIsPrivateAndIsShorts(Pageable pageable, long id,boolean status,Boolean isShort);
    void delete (Video video);
    List<Video> findVideosByWatchedUser(User user);

    Page<Video> findVideosByTitleContaining(String search, Pageable pageable);

    @Query("SELECT v.title FROM Video v WHERE v.title LIKE %:searchTitle%")
    Page<String> findVideoTitlesContaining(@Param("searchTitle") String searchTitle, Pageable pageable);
}
