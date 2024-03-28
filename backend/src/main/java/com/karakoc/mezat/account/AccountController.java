package com.karakoc.mezat.account;

import com.karakoc.mezat.user.CreateUserRequest;
import com.karakoc.mezat.user.UserDTO;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Kullanıcı Girişi", description = "Kullanıcının sisteme giriş yapmasını sağlar.")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/register")
    @Operation(summary = "Kullanıcı Kaydı", description = "Kullanıcının sisteme kayıt olmasını sağlar.")

    public UserDTO register( CreateUserRequest request) {
        return userService.register(request);
    }

    @PostMapping("/register/admin")
    @Operation(summary = "Admin user oluştur", description = "")

    public UserDTO createAdmin(CreateUserRequest request) {
        return userService.createAdmin(request);
    }


}
