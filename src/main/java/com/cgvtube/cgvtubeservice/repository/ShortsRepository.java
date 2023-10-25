package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortsRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.isShorts = true ORDER BY FUNCTION('RAND') LIMIT 1")
    Page<Video> findAllByIsShortsTrue(Pageable pageable);
}
