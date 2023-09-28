package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.entiny.Role;
import com.cgvtube.cgvtubeservice.repository.RoleRepository;
import com.cgvtube.cgvtubeservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }
}
