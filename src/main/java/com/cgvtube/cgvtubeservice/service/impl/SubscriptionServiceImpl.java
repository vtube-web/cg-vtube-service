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
    public ResponseDto subscribe(UserDetails currentUser, Long channelId) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Boolean isAlreadySubscribed  = subscriptionRepository.existsByUserIdAndSubscriberId(user.getId(), channelId);
        ResponseDto responseDto;
        if (!isAlreadySubscribed) {
            User subcriber = userRepository.findById(channelId).orElse(new User());
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

    @Override
    public ResponseDto removeSubscribed(UserDetails currentUser, Long channelId) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        int remove = subscriptionRepository.deleteByUserIdAndSubscriberId(user.getId(), channelId);
        ResponseDto responseDto;
        if (remove == 0) {
            responseDto = ResponseDto.builder().message("No subscribed found for the user with userId: " + user.getId() + " & channelId: " + channelId).status("404").data(false).build();
        } else {
            responseDto = ResponseDto.builder().message("Success remove channelId: " + channelId).status("200").data(true).build();
        }
        return responseDto;
    }

    @Override
    public ResponseDto getListChannels(UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        List<Long> listChannelId = subscriptionRepository.findSubscriberIdByUserId(user.getId());
        return ResponseDto.builder().message("Success get list subscribed channelId by userId: " + user.getId()).status("200").data(listChannelId).build();
    }
}
