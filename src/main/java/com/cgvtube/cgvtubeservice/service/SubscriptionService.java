package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entity.Subscription;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface SubscriptionService {
    ResponseDto subscribe(UserDetails currentUser, Long channelId);

    List<Long> getAllSubscribedChannels(User user);

    ResponseDto removeSubscribed(UserDetails currentUser, Long channelId);

    ResponseDto getListChannels(UserDetails currentUser);

}
