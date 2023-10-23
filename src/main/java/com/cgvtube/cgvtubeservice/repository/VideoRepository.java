package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("SELECT v FROM Video v WHERE v.user.id IN :channelIds")
    Page<Video> findVideosByChannelIds(List<Long> channelIds, Pageable pageableRequest);

    List<Video> findAll();
    Video save(Video video);
    Page<Video> findAllByUserId(Pageable pageable, long id);
    Page<Video> findAllByUserIdAndIsPrivate(Pageable pageable, long id,boolean status);
    void delete (Video video);
}
