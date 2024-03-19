package com.karakoc.mezat.user;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private String password;
    private String newPassword;

}
