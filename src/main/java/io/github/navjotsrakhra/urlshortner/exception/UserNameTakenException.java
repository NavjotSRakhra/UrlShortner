package io.github.navjotsrakhra.urlshortner.exception;


/**
 * The UserNameTakenException is an exception class used to indicate that a username is already taken during user registration.
 */
public class UserNameTakenException extends Exception {

    /**
     * Constructs a new UserNameTakenException with a default message indicating that the username is already taken.
     */
    public UserNameTakenException() {
        super("Username already taken");
    }
}
