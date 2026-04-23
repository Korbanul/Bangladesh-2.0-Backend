package com.bangladesh20.backend.Service;

import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Dto.Userservice.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface userService {
    public ProfileResponseDto getUser(Long id);

    Object updateUser(Long id, UserUpdateDto userUpdateDto);

    ResponseEntity<Long> getTotalImageCount();
}
