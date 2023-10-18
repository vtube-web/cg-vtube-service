package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.CommentShorts;
import com.cgvtube.cgvtubeservice.entity.Shorts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentShortsRepository extends JpaRepository<CommentShorts, Long> {
    List<CommentShorts> findAllByShorts(Shorts shorts);
}
