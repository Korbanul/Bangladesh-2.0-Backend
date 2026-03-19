package com.bangladesh20.backend.Controller;

import com.bangladesh20.backend.Dto.Auth.LoginRequestDto;
import com.bangladesh20.backend.Dto.Auth.LoginResponseDto;
import com.bangladesh20.backend.Dto.Auth.SignUpRequestDto;
import com.bangladesh20.backend.Dto.Auth.SignUpResponseDto;
import com.bangladesh20.backend.Service.authService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<LoginResponseDto> logIn(@RequestBody LoginRequestDto loginRequestDto,HttpServletResponse response){

            LoginResponseDto loginResponseDto=authService.Login(loginRequestDto);
            String token =loginResponseDto.getJwttoken();
    //Setting the cookie for Browser.
        ResponseCookie cookie=ResponseCookie
                .from("token",token)
                .httpOnly(true)
                .secure(true)//Allow only HTTPS
//                .secure(false) //allow HTTP For Local
                .path("/")
                .domain("bangladesh-20-backend-production.up.railway.app")
                .maxAge(60*1*10)
                .sameSite("None")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        loginResponseDto.setJwttoken(null);
        return ResponseEntity.ok(loginResponseDto);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> Logout(HttpServletResponse response){
        return ResponseEntity.ok( authService.logout(response));
    }

}
