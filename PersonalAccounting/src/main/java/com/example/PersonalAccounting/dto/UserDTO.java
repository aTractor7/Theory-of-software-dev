package com.example.PersonalAccounting.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Length(min = 8, max = 100, message = "Password should be between 8 and 100 characters")
    private String password;

    @NotEmpty(message = "Name can't be empty")
    @Length(min = 2, max = 100, message = "Name should contains less than 100 characters")
    private String name;

    private int funds;
}
