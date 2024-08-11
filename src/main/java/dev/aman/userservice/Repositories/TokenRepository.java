package dev.aman.userservice.Repositories;

import dev.aman.userservice.Models.Token;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Override
    Token save(Token token);

    Optional<Token> findByValueAndDeleted(String token, boolean deleted);

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String token, boolean deleted, Date expiryAt);
    /*
    Why did I use findByValueAndDeletedAndExpiryAtGreaterThan and not findByValueAndDeletedAndExpiryAt
    This is why:- https://chatgpt.com/share/08b3b066-eabd-4599-b043-a1b1616eae51
     */
}
