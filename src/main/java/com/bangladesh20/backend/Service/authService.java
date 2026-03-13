package com.bangladesh20.backend.Service;

import com.bangladesh20.backend.Dto.Auth.LoginRequestDto;
import com.bangladesh20.backend.Dto.Auth.LoginResponseDto;
import com.bangladesh20.backend.Dto.Auth.SignUpRequestDto;
import com.bangladesh20.backend.Dto.Auth.SignUpResponseDto;
import org.springframework.stereotype.Service;


public interface authService {
     SignUpResponseDto createUser(SignUpRequestDto signUpRequestDto);


     LoginResponseDto Login(LoginRequestDto loginRequestDto);
}
