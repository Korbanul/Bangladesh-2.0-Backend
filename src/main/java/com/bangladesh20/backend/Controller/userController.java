package com.bangladesh20.backend.Controller;
import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Dto.Donation.DonationResponseDto;
import com.bangladesh20.backend.Dto.Userservice.UserUpdateDto;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Service.donationService;
import com.bangladesh20.backend.Service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
    private final userService userService;
    private final donationService donationService;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('profile:read')")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal Users users) {  // pulled from JWT, no body needed

        return ResponseEntity.ok(userService.getUser(users.getId()));
    }

    @PatchMapping("/profile/updateprofile")
    @PreAuthorize("hasAuthority('profile:write')")
    public ResponseEntity updateUser(@AuthenticationPrincipal Users users, @RequestBody @Valid  UserUpdateDto userUpdateDto){
        //@Valid will check the Dto data with requirment set in my dto file.
        return ResponseEntity.ok(userService.updateUser(users.getId(),userUpdateDto));
    }

    @GetMapping ("/donation-list")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<DonationResponseDto>>DonationList(){
        return ResponseEntity.ok(donationService.userDonationList());
    }



}
