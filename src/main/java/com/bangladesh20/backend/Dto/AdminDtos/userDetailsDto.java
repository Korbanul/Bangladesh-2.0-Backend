package com.bangladesh20.backend.Dto.AdminDtos;

import com.bangladesh20.backend.Entity.Type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class userDetailsDto {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
    private String profession;
    private Gender gender;
    private LocalDate dob;
    private LocalDateTime Joined;
}
