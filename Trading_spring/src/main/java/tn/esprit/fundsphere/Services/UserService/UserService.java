package tn.esprit.fundsphere.Services.UserService;


import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;
import tn.esprit.fundsphere.Entities.UserManagment.ERole;
import tn.esprit.fundsphere.Entities.UserManagment.Role;
import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.Entities.UserManagment.UserStatus;
import tn.esprit.fundsphere.Repositories.TransactionRepository.PortefeuilleRepository;
import tn.esprit.fundsphere.Repositories.UserRepository.RoleRepository;
import tn.esprit.fundsphere.Repositories.UserRepository.UserRepository;
import tn.esprit.fundsphere.dto.request.ProfileUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PortefeuilleRepository portefeuilleRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
    }

    @Override
    public List<User> getUserByStatus(UserStatus status) {
        return userRepository.findByStatus(status);
    }
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
//    @Override
//    @Transactional
//    public User createUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Role userRole = roleRepository.findByName(ERole.CLIENT)
//                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
//        user.setRole(userRole);
//        return userRepository.save(user);
//    }
@Override
@Transactional
public User createUser(User user) {
    // Encode the user's password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Assign the role to the user
    Role userRole = roleRepository.findByName(ERole.CLIENT)
            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
    user.setRole(userRole);

    // Handle the Portefeuille
    Portefeuille portefeuille = user.getPortefeuille();
    if (portefeuille != null) {
        if (portefeuille.getIdPortefeuille() != null && portefeuille.getIdPortefeuille() > 0) {
            portefeuille = portefeuilleRepository.findById(portefeuille.getIdPortefeuille())
                    .orElseThrow(() -> new RuntimeException("Portefeuille not found."));
        } else {
            portefeuille.setUser(user); // Link the portefeuille to the user
        }
    }

    user.setPortefeuille(portefeuille);
    return userRepository.save(user);
}

    @Override
    @Transactional
    public void deleteUser(Long id, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        User requester = getUserByEmail(email);
        ERole requesterRole = requester.getRole().getName();
        if (requesterRole.equals(ERole.ADMIN) || requester.getId().equals(id)) {
            userRepository.delete(user);
        } else {
            throw new RuntimeException("You do not have permission to delete this user.");
        }
    }

    @Override
    @Transactional
    public void updateUserProfile(String email, ProfileUpdateRequest profileUpdateRequest) {
        User user = getUserByEmail(email);
        user.setUsername(profileUpdateRequest.getUsername());
        user.setAddress(profileUpdateRequest.getAddress());
        user.setPhoneNumber(profileUpdateRequest.getPhoneNumber());
        user.setDatedenaissance(profileUpdateRequest.getDatedenaissance());
        user.setCin(profileUpdateRequest.getCin());
        userRepository.save(user);
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        // Chercher l'utilisateur dans la base de données par son email
        Optional<User> optionalUser = userRepository.findByEmail(email);

        System.out.println("Email recherché : " + email);

        // Vérifier si l'utilisateur existe
        if (optionalUser.isEmpty()) {
            System.out.println("Utilisateur non trouvé pour l'email : " + email);
            return false; // L'utilisateur n'a pas été trouvé
        }

        User user = optionalUser.get(); // Extraire l'utilisateur de l'Optional

        // Vérifier si l'ancien mot de passe est correct
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            System.out.println("Mot de passe incorrect pour l'utilisateur : " + email);
            return false; // L'ancien mot de passe est incorrect
        }

        // Vérification que le nouveau mot de passe est différent de l'ancien
        if (oldPassword.equals(newPassword)) {
            System.out.println("Le nouveau mot de passe ne peut pas être le même que l'ancien.");
            return false; // Le nouveau mot de passe ne peut pas être identique à l'ancien
        }

        // Hachage du nouveau mot de passe et sauvegarde
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);

        userRepository.save(user); // Sauvegarder l'utilisateur avec le nouveau mot de passe haché

        System.out.println("Mot de passe changé avec succès pour l'utilisateur : " + email);
        return true;
    }


    @Override
    public List<User> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAllUsers();
        }
        return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm);
    }


    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
