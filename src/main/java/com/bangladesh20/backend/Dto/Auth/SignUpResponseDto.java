package com.bangladesh20.backend.Dto.Auth;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private String username;
    private Long id;
}
