package com.bangladesh20.backend.Controller;

import com.bangladesh20.backend.Dto.Auth.LoginRequestDto;
import com.bangladesh20.backend.Dto.Auth.LoginResponseDto;
import com.bangladesh20.backend.Dto.Auth.SignUpRequestDto;
import com.bangladesh20.backend.Dto.Auth.SignUpResponseDto;
import com.bangladesh20.backend.Service.authService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
//@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class authController {
    private  final authService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto>signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createUser(signUpRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> logIn(@RequestBody LoginRequestDto loginRequestDto){

        return ResponseEntity.ok(authService.Login(loginRequestDto));
    }
}
