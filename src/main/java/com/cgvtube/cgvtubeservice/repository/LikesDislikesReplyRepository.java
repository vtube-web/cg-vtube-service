package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.UserLikesDislikesReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesDislikesReplyRepository extends JpaRepository<UserLikesDislikesReply,Long> {
    UserLikesDislikesReply findByUserIdAndReplyId (Long idUser, Long idReply);

}
