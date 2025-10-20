package tn.esprit.fundsphere.Services.UserService;




import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.Entities.UserManagment.UserStatus;
import tn.esprit.fundsphere.dto.request.ProfileUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> findById(Long id);
    void save(User user);
    void deleteUser(Long userId, String requestingUserEmail);

    public boolean existsById(Long id);

    User getUserByEmail(String email);

    void updateUserProfile(String email, ProfileUpdateRequest profileUpdateRequest);
    boolean changePassword(String email, String oldPassword, String newPassword);
    List<User> searchUsers(String searchTerm);

    User createUser(User user);

    User getUserById(Long id);

        List<User> getUserByStatus(UserStatus status);
    }
