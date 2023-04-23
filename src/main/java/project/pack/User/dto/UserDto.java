package project.pack.User.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.pack.User.enums.Role;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "Username cannot be null!")
    @NotEmpty(message = "Username cannot be empty!")
    private String username;

    @NotNull(message = "Password cannot be null!")
    @NotEmpty(message = "Password cannot be empty!")
    private String password;

    @NotNull(message = "Role cannot be empty!")
    @Enumerated(EnumType.STRING)
    private Role role;
}