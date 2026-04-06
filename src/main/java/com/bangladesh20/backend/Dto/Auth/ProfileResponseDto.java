package com.bangladesh20.backend.Dto.Auth;

import com.bangladesh20.backend.Entity.Type.Gender;
import com.bangladesh20.backend.Entity.Type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProfileResponseDto {
    private String username;
    private String email;
//    private String Roles;
    private Set<String> roles;
    private String profession;
    private Gender gender;
    private LocalDate dob;
    private LocalDateTime Joined;


}
