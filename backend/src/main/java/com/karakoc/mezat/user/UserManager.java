package com.karakoc.mezat.user;


import com.karakoc.mezat.account.GetUserResponse;
import com.karakoc.mezat.account.LoginRequest;
import com.karakoc.mezat.account.LoginResponse;
import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.roles.UserRole;
import lombok.AllArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.karakoc.mezat.user.CreateUserRequest.*;
import static com.karakoc.mezat.user.User.*;

@Service
@AllArgsConstructor
public class UserManager implements UserService{
    private final UserRepository userRepository;

    public UserDTO register(CreateUserRequest request) {
        if (request.getUsername() == null || request.getUsername().equals("")) {
            throw new BadRequestException("Username must not be null.");
        }
        if (request.getMail() == null  || request.getMail().equals("")) {
            throw new BadRequestException("Mail must not be null.");
        }
        if (request.getFirstname() == null  || request.getFirstname().equals("")) {
            throw new BadRequestException("First name must not be null.");
        }
        if (request.getLastname() == null  || request.getLastname().equals("")) {
            throw new BadRequestException("Last name must not be null.");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().equals("")) {
            throw new BadRequestException("Phone number must not be null.");
        }
        if (request.getPassword() == null || request.getPassword().equals("")) {
            throw new BadRequestException("Password must not be null.");
        }
        if (request.getRepeatPassword() == null || request.getRepeatPassword().equals("")) {
            throw new BadRequestException("Repeat password must not be null.");
        }
        validatePhoneNumber(request.getPhoneNumber());
        validatePasswords(request.getPassword(), request.getRepeatPassword());
        validateMailAdress(request.getMail());

        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new BadRequestException("This username already exists.");
        }
        if (userRepository.findByMail(request.getMail()).isPresent()){
            throw new BadRequestException("This mail adress already exists.");
        }
        User user = userRepository.save(User.createUser(request));
        return userToDto(user);
    }

    //neler koymam gerektigini dusunuyorum.
    public UserDTO changePassword(ChangePasswordRequest request){
        if (userRepository.findByUsername(request.getUsername()).isEmpty()){
            throw new NotfoundException("User not found."); //404
        }
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (!user.get().getPassword().equals(request.getPassword())){
            throw new BadRequestException("Wrong username or password.");
        }
        user.get().setPassword(request.getNewPassword());
        User dto =   userRepository.save(user.get());
        return userToDto(dto);
    }
    public List<UserDTO> getAllUsers(){
        return usersToDtos(userRepository.findAll());
    }
    public UserDTO deleteUserById(DeleteUserByIdRequest request){

        Optional<User> user = userRepository.findById(request.getId());
        if (user.isEmpty()) {
            throw new NotfoundException("User not found.");
        }
        onlyAdminAndUserIsPresentValidation(user);
        userRepository.delete(user.get());
        return userToDto(user.get());
    }

    public UserDTO getUserById(String userId){
        Optional<User> user = userRepository.findById(userId);
        UserDTO dto;
        if (user.isPresent()){
            dto = userToDto(user.get());
            return dto;
        }
        else {
            throw new NotfoundException("User not found.");
        }

    }
    public UserDTO getUserByToken(String token){
        Optional<User> user = userRepository.findUserByToken(token);
        UserDTO dto;
        if (user.isPresent()){
            dto = userToDto(user.get());
            return dto;
        }
        else {
            throw new NotfoundException("User not found.");
        }

    }

    public GetUserResponse getUserFromUsername( String username) {
        User user1 = userRepository.findByUsername(username).orElseThrow(()-> new BadRequestException("Invalid username."));

        GetUserResponse user = new GetUserResponse();
        user.setUsername(user1.getUsername());
        user.setFirstname(user1.getFirstname());
        user.setLastname(user1.getLastname());
        user.setPhoneNumber(user1.getPhoneNumber());
        user.setUserRole(user1.getUserRole());

        return user;
    }

    public UserRole getUserRoleFromToken ( String token){
        User user1 = userRepository.findUserByToken(token).orElseThrow(()-> new BadRequestException("Invalid token."));

        return user1.getUserRole();

    }

    public UserDTO createAdmin(CreateUserRequest request){

        validatePhoneNumber(request.getPhoneNumber());
        validatePasswords(request.getPassword(), request.getRepeatPassword());
        validateMailAdress(request.getMail());

        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new BadRequestException("This username already exists.");
        }
        if (userRepository.findByMail(request.getMail()).isPresent()){
            throw new BadRequestException("This mail adress already exists.");
        }
        User user = User.createUser(request);
        user.setUserRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
        return userToDto(user);
    }

    public LoginResponse login( LoginRequest request){
        if (request.getUsername() ==null || request.getPassword() == null || request.getUsername().equals("") || request.getPassword().equals("")){
            throw new BadRequestException("Missing request body.");
        }

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


}
