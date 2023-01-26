package org.signing237.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private char gender;
    private List<String> role;
    private boolean enable;
    private boolean accountNonExpired;
    private boolean accountNonLocked;



}
