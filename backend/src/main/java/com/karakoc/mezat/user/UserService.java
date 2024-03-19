package com.karakoc.mezat.user;


import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDTO register(CreateUserRequest request);
    UserDTO changePassword(ChangePasswordRequest request);
    List<UserDTO> getAllUsers();
    UserDTO deleteUserById(DeleteUserByIdRequest request);
    UserDTO getUserById(String userId);
}
