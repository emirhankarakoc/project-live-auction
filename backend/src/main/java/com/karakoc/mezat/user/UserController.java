package com.karakoc.mezat.user;

import com.karakoc.mezat.account.GetUserResponse;
import com.karakoc.mezat.user.roles.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@Tag(name = "User Controller")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;


    @PutMapping("/changePassword")
    @Operation(summary = "Şifre değiştir", description = "")

    public UserDTO changePassword(ChangePasswordRequest request){
        return userService.changePassword(request);
    }

    @GetMapping()
    @Operation(summary = "Tüm kullanıcıları getir (kaldırılıcak)", description = "")

    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping()
    @Operation(summary = "Kullanıcı sil", description = "")

    public UserDTO deleteUserById(DeleteUserByIdRequest request){
        return userService.deleteUserById(request);
    }


    @GetMapping("/id/{id}")
    @Operation(summary = "Kullanıcıyı id'sine göre getir", description = "")

    public UserDTO getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Kullanıcı username'e göre getir", description = "")
    public GetUserResponse getUserFromUsername(@PathVariable String username) {
        return userService.getUserFromUsername(username);
    }

    @GetMapping("/token/{token}")
    @Operation(summary = "Kullanıcı'nın tokeninden rolünü bul.", description = "")

    public UserRole getUserRoleFromToken(@PathVariable String token) {
        return userService.getUserRoleFromToken(token);
    }
}
