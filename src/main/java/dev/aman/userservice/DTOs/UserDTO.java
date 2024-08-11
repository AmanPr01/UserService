package dev.aman.userservice.DTOs;

import dev.aman.userservice.Models.Role;
import dev.aman.userservice.Models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private List<Role> roles;

    public static UserDTO fromUser(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }
}


/*
We can also name this class as SignUpResponseDTO.
 */