package com.cgvtube.cgvtubeservice.repository;

import com.cgvtube.cgvtubeservice.entity.Subscription;
import com.cgvtube.cgvtubeservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Boolean existsByUserIdAndSubscriberId(Long id, Long subscriberId);

    List<Long> findSubscriptionByUser(User user);
}
