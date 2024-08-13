package dev.aman.userservice.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aman.userservice.DTOs.SendEmailEventDTO;
import dev.aman.userservice.Models.Token;
import dev.aman.userservice.Models.User;
import dev.aman.userservice.Repositories.TokenRepository;
import dev.aman.userservice.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    // By default, when we install Spring kafka, since it is coming from spring framework, it automatically gives the bean of that.
    // Object mapper comes from jackson, it converts java object to JSON.

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository,
                           KafkaTemplate kafkaTemplate,
                           ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signUp(String name, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = null;

        if (optionalUser.isPresent()) {
            // Navigate the user to login
        }
        else {
            // Create new User
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setHashedPassword(bCryptPasswordEncoder.encode(password));

            user = userRepository.save(user);

            // Publish the user inside the Queue.
            SendEmailEventDTO emailEventDTO = new SendEmailEventDTO();
            emailEventDTO.setTo(email);
            emailEventDTO.setFrom("felixfether@gmail.com");
            emailEventDTO.setSubject("Welcome to Scaler");
            emailEventDTO.setBody("Welcome to Scaler. We are very happy to have you on our platform. All the best.");

            try {
                kafkaTemplate.send(
                        "sendEmail",
                        objectMapper.writeValueAsString(emailEventDTO)
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return user;
    }

    @Override
    public Token login(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = null;

        if (optionalUser.isEmpty()) {
            // signup method
        }
        else {
            user = optionalUser.get();

            if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
                return null;
            }

            // Generate the token
            Token token = createToken(user);
            token = tokenRepository.save(token);

            return token;
        }

        return null;
    }

    private Token createToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        /*
        To set value I used Apache commons lang dependency.
         */

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);
        Date expiryAt = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        token.setExpiryAt(expiryAt);

        return token;
    }

    @Override
    public User validateToken(String token) {

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(
                token,
                false,
                new Date()
        );

        if (optionalToken.isEmpty()) {
            // throw some exception
            return null;
        }

        return optionalToken.get().getUser();
    }

    @Override
    public void logout(String token) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(token, false);

        if (optionalToken.isEmpty()) {
            // Throw some exception
        }

        Token newToken = optionalToken.get();

        newToken.setDeleted(true);
        tokenRepository.save(newToken);
    }
}
