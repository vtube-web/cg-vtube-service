package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.repository.TagRepository;
import com.cgvtube.cgvtubeservice.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    @Override
    public List<Tag> performAddAndCheckTag(List<String> hashtags) {
        List<Tag> tagList = new ArrayList<>();
        if(hashtags.size() >0){
            for(String name: hashtags){
                Tag tagẼxist=tagRepository.findByName(name);
                if(tagẼxist == null){
                    Tag tag = Tag.builder().name(name).build();
                    tagList.add(tagRepository.save(tag));
                }else {
                    tagList.add(tagRepository.save(tagẼxist));
                }
            }
        }

        return tagList;
    }
}
