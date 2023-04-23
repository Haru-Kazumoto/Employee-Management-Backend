package project.pack.User.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.pack.GlobalException.ErrorResponse;
import project.pack.User.dto.UserDto;
import project.pack.User.model.UserModel;
import project.pack.User.service.interfaces.UserService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@CrossOrigin(originPatterns = "${frontend.cors.path}")
@RequestMapping(path = "/api/v1/admin/")
public class UserController {

    private final UserService service;
    private final ModelMapper mapper;

    @GetMapping(path = "get-all")
    public ResponseEntity<List<?>> getAllRecord(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllUser());
    }

    @GetMapping(path = "get-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) throws NoSuchElementException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(
                            HttpStatus.NOT_FOUND,
                            ex.getMessage()
                    ));
        }
    }

    @PostMapping(path = "create")
    public ResponseEntity<?> createRecord(@RequestBody @Valid UserDto bodyUser){
        try {
            UserModel data = mapper.map(bodyUser, UserModel.class);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.createUser(data));
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(
                            HttpStatus.NOT_FOUND,
                            ex.getMessage()
                    ));
        }
    }
}