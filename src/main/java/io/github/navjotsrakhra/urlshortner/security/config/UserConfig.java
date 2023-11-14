/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.urlshortner.security.config;

import io.github.navjotsrakhra.urlshortner.security.data.model.UserData;
import io.github.navjotsrakhra.urlshortner.security.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The UserConfig class is responsible for configuring the user details service.
 */
@Configuration
public class UserConfig {
    /**
     * Configure the UserDetailsService bean to handle user authentication.
     *
     * @param userRepository The UserRepository used for retrieving user details.
     * @return A UserDetailsService implementation that retrieves user information from the UserRepository.
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserData userDa = userRepository.findByUsername(username);
            if (userDa == null) throw new UsernameNotFoundException("Username: " + username + " not found.");
            return userDa;
        };
    }
}
