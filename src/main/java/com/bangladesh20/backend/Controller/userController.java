package com.bangladesh20.backend.Controller;
import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Dto.Userservice.UserUpdateDto;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
    private final userService userService;
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal Users users) {  // pulled from JWT, no body needed

        return ResponseEntity.ok(userService.getUser(users.getId()));
    }

    @PatchMapping("/profile/updateprofile")
    public ResponseEntity updateUser(@AuthenticationPrincipal Users users, @RequestBody @Valid  UserUpdateDto userUpdateDto){
        //@Valid will check the Dto data with requirment set in my dto file.
        return ResponseEntity.ok(userService.updateUser(users.getId(),userUpdateDto));
    }
}
