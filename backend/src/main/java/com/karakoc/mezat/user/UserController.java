package com.karakoc.mezat.user;

import com.karakoc.mezat.account.GetUserResponse;
import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.user.roles.UserRole;
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


    @PostMapping()
    public UserDTO createUser( CreateUserRequest req){
        return userService.register(req);
    }

    @PutMapping("/changePassword")
    public UserDTO changePassword(ChangePasswordRequest request){
        return userService.changePassword(request);
    }
    @GetMapping()
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping()
    public UserDTO deleteUserById(DeleteUserByIdRequest request){
        return userService.deleteUserById(request);
    }

    @PostMapping("/admin")
    public UserDTO createAdmin(CreateUserRequest request){
        return userService.createAdmin(request);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable  String userId){
        return userService.getUserById(userId);
    }
    @GetMapping("/{username}")
   public GetUserResponse getUserFromUsername(@PathVariable String username) throws InterruptedException{
        return userService.getUserFromUsername(username);
    }
    @GetMapping("/{token}")
    public UserRole getUserRoleFromToken (@PathVariable String token){
        return userService.getUserRoleFromToken(token);
    }
}
