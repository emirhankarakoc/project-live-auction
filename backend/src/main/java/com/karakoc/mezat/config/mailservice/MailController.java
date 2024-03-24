package com.karakoc.mezat.config.mailservice;

import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailController {
    private final MailService service;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/to/{userToken}/adminToken/{adminToken}")
    public String sendMailWithoutAttachment(@PathVariable String userToken,@PathVariable String adminToken, String mesaj){
        String email = userService.getUserByToken(userToken).getMail();
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));


        return service.sendMail(email,mesaj);
    }
}
