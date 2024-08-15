package dev.aman.userservice.Services;

import dev.aman.userservice.Models.Token;
import dev.aman.userservice.Models.User;

public interface UserService {

    User signUp(String name, String email, String password);

    Token login(String email, String password);

    User validateToken(String token);

    void logout(String token);

    User getUserDetails(Long suerID);
}
