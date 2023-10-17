package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends PagingAndSortingRepository<Video,Long> {
    Video findById(Long videoId);
    List<Video> findAll();
    Video save(Video video);
    Page<Video> findAllByUserId(Pageable pageable, long id);
    Page<Video> findAllByUserIdAndIsPrivate(Pageable pageable, long id,boolean status);
    Boolean delete (Video video);

}
