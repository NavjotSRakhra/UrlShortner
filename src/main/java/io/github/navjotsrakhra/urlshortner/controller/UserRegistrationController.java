package io.github.navjotsrakhra.urlshortner.controller;

import io.github.navjotsrakhra.urlshortner.data.model.dto.UserFormDTO;
import io.github.navjotsrakhra.urlshortner.exception.UserNameTakenException;
import io.github.navjotsrakhra.urlshortner.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The UserRegistrationController class handles HTTP requests related to user registration.
 */
@RestController
@RequestMapping("/register")
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;
    private final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    /**
     * Constructor for the UserRegistrationController class.
     *
     * @param userRegistrationService Service for user registration.
     */
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public ResponseEntity<Void> register(String username, String password, String email) throws UserNameTakenException {
        log.info("Registering username: {}, email: {}", username, email);
        return userRegistrationService.saveUserWithDefaultRole(new UserFormDTO(username, password, email));
    }
}
