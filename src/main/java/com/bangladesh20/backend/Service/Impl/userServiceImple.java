package com.bangladesh20.backend.Service.Impl;


import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.authRepository;
import com.bangladesh20.backend.Service.userService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class userServiceImple implements userService {
    private final authRepository authRepository;

    @Override
    public ProfileResponseDto getUser(Long id) {
        Users user = authRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));


        return new ProfileResponseDto(user.getUsername(), user.getEmail(), user.getRoles().stream()
                .map(role -> role.name())
                .collect(java.util.stream.Collectors.joining(", ")),user.getProfession(),user.getGender(),user.getDob(),user.getCreatedAt());
    }
}

