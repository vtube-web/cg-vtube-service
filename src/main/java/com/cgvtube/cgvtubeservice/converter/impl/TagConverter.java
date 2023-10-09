package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.payload.response.TagResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagConverter implements GeneralConverter<Tag, TagResponseDto> {
    @Override
    public TagResponseDto convert(Tag source) {
        TagResponseDto target = new TagResponseDto();
        BeanUtils.copyProperties(source,target);
        return target;
    }

    @Override
    public Tag revert(TagResponseDto target) {
        return null;
    }

    @Override
    public List<TagResponseDto> convert(List<Tag> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Tag> revert(List<TagResponseDto> targets) {
        return null;
    }
}
