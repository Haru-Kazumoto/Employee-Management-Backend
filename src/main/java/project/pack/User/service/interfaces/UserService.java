package project.pack.User.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.pack.User.model.UserModel;
import project.pack.User.response.GetResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<GetResponse> getAllUser();
    UserModel createUser(UserModel userModel);
    Optional<GetResponse> findById(UUID id);
    String deleteUser(String id);
    UserModel updateUser(String id,UserModel userModel);
}
