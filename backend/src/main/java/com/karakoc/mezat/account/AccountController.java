package com.karakoc.mezat.account;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.*;
import com.karakoc.mezat.user.roles.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static com.karakoc.mezat.user.User.userToDto;


@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
       return userService.login(request);
    }
    @PostMapping("/register")
    public UserDTO register(@RequestBody CreateUserRequest request){
        return userService.register(request);
    }
    

}
