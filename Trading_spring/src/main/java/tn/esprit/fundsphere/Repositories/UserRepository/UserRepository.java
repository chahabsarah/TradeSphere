package tn.esprit.fundsphere.Repositories.UserRepository;

import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.Entities.UserManagment.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
List<User>findByStatus(UserStatus status);
    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN u.role r WHERE " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    List<User> searchByTerm(@Param("searchTerm") String searchTerm);

    @Query("SELECT r.name, COUNT(u) FROM User u JOIN u.role r GROUP BY r.name")
    List<Object[]> countUsersByRole();
}
