package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByVideo(Video video);
    List<Comment> findAllByVideoId(long id);
    Page<Comment> findAllByUserId(Pageable pageable, Long id);
}
