/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.urlshortner.security.repository;

import io.github.navjotsrakhra.urlshortner.security.data.model.UserData;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The UserRepository interface provides data access methods for interacting with UserData entities.
 */
public interface UserRepository extends ListCrudRepository<UserData, Long>, PagingAndSortingRepository<UserData, Long> {

    /**
     * Find a user by their username.
     *
     * @param username The username to search for.
     * @return The UserObject with the specified username, or null if not found.
     */
    UserData findByUsername(String username);
}
