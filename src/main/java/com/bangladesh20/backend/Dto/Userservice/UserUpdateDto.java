package com.bangladesh20.backend.Dto.Userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserUpdateDto {
    @NotBlank(message = "Username is Required")
    @Size(min = 3, max = 26, message = "Name must be between 3-26 character")
    private String username;

    @NotBlank(message = "Profession is Required")
    private String profession;

    @NotBlank(message = "Gender is Required")
    private String gender;

    @NotNull(message = "Date of Birth is Required")
    private LocalDate dob;
}
