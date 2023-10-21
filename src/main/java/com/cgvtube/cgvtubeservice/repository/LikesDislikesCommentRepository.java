package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.UserLikesDislikesComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesDislikesCommentRepository extends JpaRepository<UserLikesDislikesComments, Long> {
    UserLikesDislikesComments findByUserIdAndCommentId(Long idUser, Long idComment);

    void deleteAllByCommentId(Long id);
}
