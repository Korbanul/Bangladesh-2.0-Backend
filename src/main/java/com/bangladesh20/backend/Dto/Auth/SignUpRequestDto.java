package com.bangladesh20.backend.Dto.Auth;

import lombok.*;
import org.aspectj.bridge.IMessage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpRequestDto {
    @NotBlank(message="Username is Required")
    @Size(min = 3, max = 26, message = "Username must be between 3 and 20 characters")

    private String username;
    @Email
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    private String password;


}
