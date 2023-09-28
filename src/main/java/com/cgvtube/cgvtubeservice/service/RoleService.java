package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entiny.Role;
import org.springframework.stereotype.Service;


public interface RoleService {
    Role findById(Long id);
}
