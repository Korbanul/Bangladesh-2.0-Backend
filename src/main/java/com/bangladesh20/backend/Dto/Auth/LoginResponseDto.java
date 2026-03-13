package com.bangladesh20.backend.Dto.Auth;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String jwttoken;


}
