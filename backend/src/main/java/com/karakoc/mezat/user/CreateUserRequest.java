package com.karakoc.mezat.user;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstname;
    private String lastname;

    private String phoneNumber;
    private String username;
    private String password;
    private String repeatPassword;
    private String mail;



    public static void validatePhoneNumber(String phoneNumber){
        if (phoneNumber.length()!=10){
            throw new BadRequestException("Phone number length must be 10.");
        }

    }
//buraya assert true koy.
public static void validatePasswords(String password, String repeatPassword) {
    if (password.length() <= 7) {
        throw new BadRequestException("Password must be at least 8 characters long.");
    }
    if (!password.equals(repeatPassword)) {
        throw new BadRequestException("Passowrds do not matching.");
    }
}

public static void validateMailAdress(String mail){
        if (!mail.contains("@")  || !mail.contains(".com") || mail.length()<10){
            throw new BadRequestException("Invalid mail type.");
        }
}
}
