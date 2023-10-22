package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.UserLikesDislikesComments;
import com.cgvtube.cgvtubeservice.payload.response.LikesDislikesCommentsResDto;
import com.cgvtube.cgvtubeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LikesDislikesCommentsResDtoConverter implements Function <List<UserLikesDislikesComments>,LikesDislikesCommentsResDto> {
    private final UserService userService;
    @Override
    public LikesDislikesCommentsResDto apply(List<UserLikesDislikesComments> userLikesDislikesComments) {
        Long id = userService.getCurrentUser().getId();
        LikesDislikesCommentsResDto likesDislikesCommentsResDtos = new LikesDislikesCommentsResDto();
        for (UserLikesDislikesComments element : userLikesDislikesComments){
            if (element.getUser().getId().equals(id)){
                BeanUtils.copyProperties(element,likesDislikesCommentsResDtos);
                likesDislikesCommentsResDtos.setUserId(element.getUser().getId());
            }
        }
        return likesDislikesCommentsResDtos;
    }
}
