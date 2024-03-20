package com.karakoc.mezat.account;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;


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

}
