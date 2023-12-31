package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.CommentShorts;
import com.cgvtube.cgvtubeservice.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentShortsRepository extends JpaRepository<CommentShorts, Long> {
    List<CommentShorts> findAllByVideo(Video video);
}
