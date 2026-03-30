package com.bangladesh20.backend.Service.Impl;

import com.bangladesh20.backend.Dto.Auth.LoginRequestDto;
import com.bangladesh20.backend.Dto.Auth.LoginResponseDto;
import com.bangladesh20.backend.Dto.Auth.SignUpRequestDto;
import com.bangladesh20.backend.Dto.Auth.SignUpResponseDto;
import com.bangladesh20.backend.Entity.Type.Gender;
import com.bangladesh20.backend.Entity.Type.RoleType;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.authRepository;

import java.util.Collections;

import com.bangladesh20.backend.Security.JwtTokenGenerate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;


@Service
@AllArgsConstructor
public class authServiceimpl implements com.bangladesh20.backend.Service.authService {
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final authRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerate jwtTokenGenerate;

    @Override
    public SignUpResponseDto createUser(SignUpRequestDto signUpRequestDto) {
        if (authRepository.findByUsername(signUpRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User Already Exists");
        }

        Users users = Users.builder()
                .username(signUpRequestDto.getUsername())
                .profession(signUpRequestDto.getProfession())
                .gender(Gender.valueOf(signUpRequestDto.getGender().toUpperCase()))
                .dob(signUpRequestDto.getDob())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .Roles(Collections.singleton(RoleType.USER))
                .build();
        users = authRepository.save(users);

        return modelMapper.map(users, SignUpResponseDto.class);
    }

    @Override
    public LoginResponseDto Login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        Users user = (Users) authentication.getPrincipal();
        String jwt =jwtTokenGenerate.JwtTokenGenerate(user);

        return new LoginResponseDto(user.getId(),jwt,user.getUsername());
    }

    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) {
//        ResponseCookie cookie = ResponseCookie.from("token", "")
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(0) // delete cookie
//                .build();
//
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Logout Successful");
    }
}
