package project.pack.Authentication.service;

import org.springframework.security.authentication.BadCredentialsException;
import project.pack.Authentication.io.AuthRequest;
import project.pack.User.model.UserModel;

public interface AuthService {
    String authenticate(AuthRequest request) throws BadCredentialsException;
    UserModel authorization(UserModel bodyModelRequest);
}
