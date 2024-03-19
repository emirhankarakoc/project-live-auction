package com.karakoc.mezat.user;

import com.karakoc.mezat.exceptions.general.ForbiddenException;
import com.karakoc.mezat.exceptions.general.UnauthorizatedException;
import com.karakoc.mezat.user.roles.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor

public class User {
    @Id
    private String id; //
    private String firstname;//
    @Enumerated
    private UserRole userRole;//
    private String lastname;//
    private String phoneNumber;//
    private String username;
    private String password;//
    private String token;//
    private String mail;//




    public static User createUser(CreateUserRequest request){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setMail(request.getMail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setToken(UUID.randomUUID().toString());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUserRole(UserRole.ROLE_USER);
        return user;
    }
    //9 field var. hepsini setledim.

    public static UserDTO userToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setMail(user.getMail());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setToken(user.getToken());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setUserRole(user.getUserRole());
        return dto;
    }
    public static List<UserDTO> usersToDtos(List<User> userList){
        List<UserDTO> response = new ArrayList<>();
        for (User user : userList) {
            response.add(userToDto(user));
        }
        return response;
    }
    public static void onlyAdminAndUserIsPresentValidation(Optional<User> user){
        if (user.isPresent() &&  user.get().getUserRole().equals(UserRole.ROLE_USER)){
            throw new ForbiddenException("Forbidden.");
        }
        if (user.isEmpty()){
            throw new UnauthorizatedException("Unauthorized.");
        }
    }



}


