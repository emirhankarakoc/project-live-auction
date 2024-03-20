package com.karakoc.mezat.account;


import com.karakoc.mezat.user.roles.UserRole;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserResponse {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String username;

    @Enumerated
    private UserRole userRole;
}
