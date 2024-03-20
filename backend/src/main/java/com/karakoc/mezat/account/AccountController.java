package com.karakoc.mezat.account;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserDTO;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.UserService;
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
        User user = userRepository.findByMail(request.getUsername()).orElseThrow(()-> new BadRequestException("Invalid username or password."));
        LoginResponse response = new LoginResponse();

        if (user.getPassword().equals(request.getPassword())){
        response.setUsername(user.getUsername());
        response.setToken(user.getToken());
        return response;
        }else{
            throw new BadRequestException("Invalid username or password.");
        }

    }


    @GetMapping("/{username}")
    public GetUserResponse getUserFromUsername(@PathVariable String username) throws InterruptedException {

        Thread.sleep(5000);
        // todo burayi duzelt
        User user1 = userRepository.findByUsername(username).orElseThrow(()-> new BadRequestException("Invalid username."));

        GetUserResponse user = new GetUserResponse();
        user.setUsername(user1.getUsername());
        user.setFirstname(user1.getFirstname());
        user.setLastname(user1.getLastname());
        user.setPhoneNumber(user1.getPhoneNumber());
        user.setUserRole(user1.getUserRole());

        return user;
    }

    @GetMapping("/token/{token}")
    public UserRole getUserFromToken (@PathVariable String token){
        User user1 = userRepository.findUserByToken(token).orElseThrow(()-> new BadRequestException("Invalid token."));

        return user1.getUserRole();

    }

}
