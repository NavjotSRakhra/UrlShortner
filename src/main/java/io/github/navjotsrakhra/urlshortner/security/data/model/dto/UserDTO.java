/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.urlshortner.security.data.model.dto;


import io.github.navjotsrakhra.urlshortner.security.data.model.Role;

import java.util.List;

/**
 * The UserDTO record represents an exposed version of a UserData, providing simplified access to user data.
 */
public record UserDTO(long id, String username, List<Role> roles, boolean accountLocked,
                      boolean accountExpired, boolean credentialsExpired) {

}
