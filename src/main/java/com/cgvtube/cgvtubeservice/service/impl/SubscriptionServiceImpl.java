package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.entity.Subscription;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.SubscriptionRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto subscribe(UserDetails currentUser, Long subscriberId) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Boolean isAlreadySubscribed  = subscriptionRepository.existsByUserIdAndSubscriberId(user.getId(), subscriberId);
        ResponseDto responseDto;
        if (!isAlreadySubscribed) {
            User subcriber = userRepository.findById(subscriberId).orElse(new User());
            Subscription subscription = new Subscription(user, subcriber, LocalDateTime.now());
            subscriptionRepository.save(subscription);
            responseDto = ResponseDto.builder().message("Successfully subscribed to the channel").status("200").data(true).build();
        } else {
            responseDto = ResponseDto.builder().message("You are already subscribed to this user").status("404").data(false).build();
        }
    return responseDto;
    }

    @Override
    public List<Long> getAllSubscribedChannels(User user) {
        List<Long> channelsList = subscriptionRepository.findSubscriptionByUser(user);
        return channelsList;
    }
}
