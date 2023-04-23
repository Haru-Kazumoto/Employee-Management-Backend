package project.pack.Authentication.io;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @NotEmpty(message = "Username is blank")
    private String username;
    @NotEmpty(message = "Password is blank")
    private String password;
}
