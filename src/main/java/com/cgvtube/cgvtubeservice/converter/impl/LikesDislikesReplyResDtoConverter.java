package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.UserLikesDislikesReply;
import com.cgvtube.cgvtubeservice.payload.response.LikesDislikesReplyResDto;
import com.cgvtube.cgvtubeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LikesDislikesReplyResDtoConverter implements Function<List<UserLikesDislikesReply>, LikesDislikesReplyResDto> {
    private final UserService userService;
    @Override
    public LikesDislikesReplyResDto apply(List<UserLikesDislikesReply> userLikesDislikesReplies) {
        Long id = userService.getCurrentUser().getId();
        LikesDislikesReplyResDto likesDislikesReplyResDto = new LikesDislikesReplyResDto();
        for (UserLikesDislikesReply element : userLikesDislikesReplies){
            if (element.getUser().getId().equals(id)){
                BeanUtils.copyProperties(element,likesDislikesReplyResDto);
                likesDislikesReplyResDto.setUserId(element.getUser().getId());
            }
        }
        return likesDislikesReplyResDto;
    }
}
