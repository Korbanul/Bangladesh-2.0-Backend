package com.bangladesh20.backend.Dto.Auth;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message="Username is Required")
    @Size(min = 3, max = 26, message = "Username must be between 3 and 26 characters")
    private String username;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Profession is Required")
    private String profession;

    @NotBlank(message = "Gender is Required")
    private String gender;

    @NotNull(message = "Date of Birth is Required") //For date type must use NotNull . NotBlank is not allowed for date
    private LocalDate dob;

    @Size(min = 6, message = "Password must be at least  characters")
    @NotBlank(message = "Password is Required")
    private String password;



}
