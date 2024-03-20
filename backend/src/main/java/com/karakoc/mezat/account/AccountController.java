package com.karakoc.mezat.account;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserDTO;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.UserService;
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
    public String login(@RequestBody LoginRequest request){
        User user = userRepository.findByMail(request.getUsername()).orElseThrow(()-> new BadRequestException("Invalid username or password."));
        if (user.getPassword().equals(request.getPassword())){
            return user.getToken();
        }else{
            throw new BadRequestException("Invalid username or password.");
        }

    }


    @GetMapping("/{token}")
    public UserDTO getUserFromToken(@PathVariable String token){
        User user = userRepository.findUserByToken(token).orElseThrow(()-> new BadRequestException("Invalid token."));
        return userToDto(user);
    }

}
