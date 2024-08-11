package dev.aman.userservice.DTOs;

import dev.aman.userservice.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDTO {
    private User user;
    private ResponseStatus responseStatus;
}
