package dev.aman.userservice.DTOs;

import dev.aman.userservice.Models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
     private Token token;
}
