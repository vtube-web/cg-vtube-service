package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Subscription;
import com.cgvtube.cgvtubeservice.payload.response.SubscriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class SubscriptionConverter implements GeneralConverter<SubscriptionDTO, Subscription> {
    @Override
    public Subscription convert(SubscriptionDTO source) {
        return null;
    }

    @Override
    public SubscriptionDTO revert(Subscription target) {
        return SubscriptionDTO.builder()
                .channelId(target.getSubscriber().getId())
                .subscribeAt(target.getSubscribeAt())
                .build();
    }

    @Override
    public List<Subscription> convert(List<SubscriptionDTO> sources) {
        return null;
    }

    @Override
    public List<SubscriptionDTO> revert(List<Subscription> targets) {
        return null;
    }
}
