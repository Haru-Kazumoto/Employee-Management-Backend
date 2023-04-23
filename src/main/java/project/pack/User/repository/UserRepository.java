package project.pack.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.pack.User.model.UserModel;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findById(UUID id);
}
