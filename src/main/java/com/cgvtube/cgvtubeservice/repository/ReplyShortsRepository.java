package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.ReplyShorts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyShortsRepository extends JpaRepository<ReplyShorts, Long> {
}
