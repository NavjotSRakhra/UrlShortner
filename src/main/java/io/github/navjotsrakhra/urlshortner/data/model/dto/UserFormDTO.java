package io.github.navjotsrakhra.urlshortner.data.model.dto;

import io.github.navjotsrakhra.urlshortner.security.data.model.Role;
import io.github.navjotsrakhra.urlshortner.security.data.model.UserData;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public record UserFormDTO(@NotNull @NotBlank String username, @NotNull @NotBlank String password,
                          @NotNull @NotBlank @Email String email) {
    public UserData toUserData(PasswordEncoder encoder) {
        return new UserData(username, List.of(Role.USER), encoder.encode(password), email, false, false, false);
    }
}
