package com.projectalpha.projectalpha.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String emailId;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 15, message = "Password must be between 6 and 15 characters long")
    private String password;
}
