package com.bangladesh20.backend.Dto.Auth;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequestDto {
    private String username;
    private String password;
}
