package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.repository.TagRepository;
import com.cgvtube.cgvtubeservice.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
}
