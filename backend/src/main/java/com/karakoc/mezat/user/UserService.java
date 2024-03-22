package com.karakoc.mezat.user;


import com.karakoc.mezat.account.GetUserResponse;
import com.karakoc.mezat.account.LoginRequest;
import com.karakoc.mezat.account.LoginResponse;
import com.karakoc.mezat.user.roles.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

    UserDTO register(CreateUserRequest request);
    UserDTO changePassword(ChangePasswordRequest request);
    List<UserDTO> getAllUsers();
    UserDTO deleteUserById(DeleteUserByIdRequest request);
    UserDTO getUserById(String userId);
    GetUserResponse getUserFromUsername(String username) throws InterruptedException;
    UserRole getUserRoleFromToken (String token);
    UserDTO createAdmin(CreateUserRequest request);
    LoginResponse login( LoginRequest request);
}
