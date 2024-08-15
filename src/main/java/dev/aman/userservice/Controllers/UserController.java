package dev.aman.userservice.Controllers;

import dev.aman.userservice.DTOs.*;
import dev.aman.userservice.DTOs.ResponseStatus;
import dev.aman.userservice.Models.Token;
import dev.aman.userservice.Models.User;
import dev.aman.userservice.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO requestDTO) {

        Token token = userService.login(
                requestDTO.getEmail(),
                requestDTO.getPassword()
        );

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);

        return responseDTO;
    }

    @PostMapping("/signup")
    public SignUpResponseDTO signUp(@RequestBody SignUpRequestDTO requestDTO) {

        User suer = userService.signUp(
                requestDTO.getName(),
                requestDTO.getEmail(),
                requestDTO.getPassword()
        );

        SignUpResponseDTO responseDTO = new SignUpResponseDTO();
        responseDTO.setUser(suer);
        responseDTO.setResponseStatus(ResponseStatus.SUCCESS);

        return responseDTO;
    }

    @GetMapping("/validate/{token}")
    public UserDTO validateToken(@PathVariable("token") String token) {

        User user = userService.validateToken(token);

        return UserDTO.fromUser(user);
    }

    // Just to return the response entity of void.
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO requestDTO) {
        ResponseEntity<Void> responseEntity = null;
        try {
            userService.logout(requestDTO.getToken());

            responseEntity = new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(
                    HttpStatus.UNAUTHORIZED
            );
        }

        return responseEntity;
    }
    /*
    So logout was not working, because for token we are checking if deleted is false.
    But in database deleted is null, so to correct it we have to set deleted from the database as false.
    Go to database in the intellij idea itself and double-click the deleted column and change option to false.
    After that you have to submit it by clicking on the submit button marked as an "upward arrow".
     */

    // this is for testing ServiceDiscovery
    @GetMapping("/{id}")
    public UserDTO getUserDetails(@PathVariable("id") Long userId) {
        System.out.println("Received getUserDetails API request");
        return UserDTO.fromUser(userService.getUserDetails(userId));
    }

}
