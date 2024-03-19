package com.karakoc.mezat.user;


import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.roles.UserRole;
import lombok.AllArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.stereotype.Service;

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
        if (userRepository.findAll().size()==0){
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUserRole(UserRole.ROLE_ADMIN);
            user.setToken(UUID.randomUUID().toString());
            User user1 = new User();
            user1.setId(UUID.randomUUID().toString());
            user1.setToken("1");
            user1.setUserRole(UserRole.ROLE_ADMIN);
            userRepository.save(user);
            userRepository.save(user1);

        }
        validatePhoneNumber(request.getPhoneNumber());
        validatePasswords(request.getPassword(), request.getRepeatPassword());
        validateMailAdress(request.getMail());

        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new BadRequestException("This username already exists.");
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

}
