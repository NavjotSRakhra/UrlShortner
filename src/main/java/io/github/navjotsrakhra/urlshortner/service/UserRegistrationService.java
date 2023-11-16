package io.github.navjotsrakhra.urlshortner.service;

import io.github.navjotsrakhra.urlshortner.data.model.dto.UserFormDTO;
import io.github.navjotsrakhra.urlshortner.exception.UserNameTakenException;
import io.github.navjotsrakhra.urlshortner.security.repository.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The UserRegistrationService class is responsible for handling user registration related operations.
 */
@Service
public class UserRegistrationService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final Logger log = LoggerFactory.getLogger(UserRegistrationService.class);

    /**
     * Constructor for the UserRegistrationService class.
     *
     * @param repository The UserRepository used for saving user data.
     * @param encoder    The PasswordEncoder used for encoding user passwords.
     */
    public UserRegistrationService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    /**
     * Save a user from a user form with the default role.
     *
     * @param userFormDTO The UserFormDTO object containing user data.
     * @throws UserNameTakenException If the username is already taken.
     */
    public ResponseEntity<Void> saveUserWithDefaultRole(@Valid UserFormDTO userFormDTO) throws UserNameTakenException {
        var user = userFormDTO.toUserData(encoder);
        log.trace("Encoder class {}", encoder.getClass());
        try {
            repository.save(user);
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key value violates")) {
                log.trace("Username taken: {}", user.getUsername(), e);
                throw new UserNameTakenException();
            } else throw e;
        }
        log.info("User registered: {}", user.getUsername());
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header("Location", "static/login")
                .build();
    }
}
