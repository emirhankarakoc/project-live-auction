package com.karakoc.mezat.user;

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

}
