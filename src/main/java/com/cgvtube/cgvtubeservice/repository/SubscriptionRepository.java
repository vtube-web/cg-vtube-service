package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.Subscription;
import com.cgvtube.cgvtubeservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Boolean existsByUserIdAndSubscriberId(Long id, Long subscriberId);

    List<Long> findSubscriptionByUser(User user);

    int deleteByUserIdAndSubscriberId(Long id, Long channelId);

    List<Long> findSubscriberIdByUserId(Long id);

}
