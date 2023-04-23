package project.pack.Authentication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.pack.Authentication.io.AuthRequest;
import project.pack.Authentication.service.AuthService;
import project.pack.GlobalException.ErrorResponse;
import project.pack.User.dto.UserDto;
import project.pack.User.model.UserModel;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth/")
public class AuthController {

    private final AuthService authService;
    private final ModelMapper mapper;

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest body){
        try{
            return ResponseEntity
                    .accepted()
                    .body(authService.authenticate(body));
        } catch (BadCredentialsException exception) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            HttpStatus.UNAUTHORIZED,
                            exception.getMessage()
                    ));
        }
    }

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto user) {
        try{
            UserModel modelMapping = mapper.map(user, UserModel.class);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(authService.authorization(modelMapping));
        } catch (DataIntegrityViolationException exception){
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(
                            HttpStatus.CONFLICT,
                            exception.getMessage()
                    ));
        }
    }

}
