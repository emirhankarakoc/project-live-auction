package com.karakoc.mezat.user;

import lombok.Data;

@Data
public class DeleteUserByIdRequest {
    private String id;
    private String adminToken;
}
