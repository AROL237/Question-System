package org.signing237.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class CustomerRequest {
    @Email
    private String email;
    @NotBlank(message = "password field cannot be blank")
    @NotNull(message = "password cannot be null")
    @Length(min = 8,message = "password length should be at least 8 characters long")
    private String password;
    @Length(min = 4,message = "the first name should be more than 4 character")
    private String firstName;
    @Length(min = 4,message = "the last name should be more than 4 character")
    private String lastName;
    private char gender;
}
