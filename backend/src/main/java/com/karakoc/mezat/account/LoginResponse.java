package com.karakoc.mezat.account;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String username;
}
