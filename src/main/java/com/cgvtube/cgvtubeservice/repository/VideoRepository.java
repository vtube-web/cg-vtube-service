package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entiny.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
}
