package project.pack.User.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.pack.User.model.UserModel;
import project.pack.User.repository.UserRepository;
import project.pack.User.response.GetResponse;
import project.pack.User.response.GetResponseMapper;
import project.pack.User.service.interfaces.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final GetResponseMapper responseMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, String> errorMessage = new HashMap<>();
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        errorMessage.put("message","Username ["+username+"]not found!")));
    }

    @Override
    public List<GetResponse> getAllUser() {
        return repository
                .findAll()
                .stream()
                .map(responseMapper)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackOn = {DataIntegrityViolationException.class})
    @Override
    public UserModel createUser(UserModel userModel) {
        Optional<UserModel> isUsernameExists = repository.findByUsername(userModel.getUsername());
        if (isUsernameExists.isPresent()){
            throw new DataIntegrityViolationException(
                    String.format("Username %s already exists", userModel.getUsername())
            );
        }
        return repository.save(userModel);
    }

    @Override
    public Optional<GetResponse> findById(UUID id) throws NoSuchElementException{
        return Optional.ofNullable(
                repository
                    .findById(id)
                    .map(responseMapper)
                    .orElseThrow(
                            () -> new NoSuchElementException(
                                    String.format("Id %s not found!", id))
                    )
        );
    }

    @Transactional(rollbackOn = {EntityNotFoundException.class})
    @Override
    public String deleteUser(String id) throws EntityNotFoundException{
        Map<String, String> error = new HashMap<>();
        Optional<UserModel> findId = Optional.ofNullable(repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        error.put("message",String.format("Id %s Not found",id))
                )));
        if(findId.isPresent()){
            repository.deleteById(id);
        }
        return String.format("Id %s has deleted",id);
    }

    @Transactional(rollbackOn = {DataIntegrityViolationException.class, DuplicateKeyException.class})
    @Override
    public UserModel updateUser(String id, UserModel userModel) {
        return null;
    }
}
