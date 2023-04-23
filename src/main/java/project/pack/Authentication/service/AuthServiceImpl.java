package project.pack.Authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.pack.Authentication.io.AuthRequest;
import project.pack.User.model.UserModel;
import project.pack.User.repository.UserRepository;
import project.pack.User.service.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String authenticate(AuthRequest request) throws BadCredentialsException {
        UserModel dataUser = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username is wrong!"));
        if(!passwordEncoder.matches(request.getPassword(), dataUser.getPassword())){
            throw new BadCredentialsException("Password is wrong!");
        }

        return "Auth success! welcome!";
    }

    @Override
    public UserModel authorization(UserModel bodyModelRequest) {
        bodyModelRequest.setPassword(
                passwordEncoder.encode(bodyModelRequest.getPassword())
        );
        return userService.createUser(bodyModelRequest);
    }
}
