package com.karakoc.mezat.user;

import com.karakoc.mezat.user.roles.UserRole;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String firstname;
    private String lastname;

    private String phoneNumber;
    private String username;
    private String password;
    private String token;
    private String mail;
    @Enumerated
    private UserRole userRole;//
}
