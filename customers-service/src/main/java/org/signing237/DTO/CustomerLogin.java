package org.signing237.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerLogin {
    @NotNull(message = "email address is required")
    private String email;
    @NotNull(message = "password is required")
    private String password;
}
