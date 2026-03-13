package com.bangladesh20.backend.Dto.Auth;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private String username;
    private Long id;
}
