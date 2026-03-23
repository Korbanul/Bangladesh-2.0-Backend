package com.bangladesh20.backend.Controller;
import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
    private final userService userService;
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(
            @AuthenticationPrincipal Users users) {  // pulled from JWT, no body needed

        return ResponseEntity.ok(userService.getUser(users.getId()));
    }
}
