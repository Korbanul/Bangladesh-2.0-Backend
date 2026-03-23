package com.bangladesh20.backend.Service;

import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface userService {
    public ProfileResponseDto getUser(Long id);
}
