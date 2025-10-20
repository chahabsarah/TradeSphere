package tn.esprit.fundsphere.Controllers.UserRestController;

import tn.esprit.fundsphere.Entities.UserManagment.ERole;
import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.Services.UserService.IUserService;
import tn.esprit.fundsphere.Services.UserService.UserDetailsImpl;
import tn.esprit.fundsphere.Services.UserService.UserService;
import tn.esprit.fundsphere.dto.request.AccountValidationRequest;
import tn.esprit.fundsphere.dto.request.ChangePasswordRequest;
import tn.esprit.fundsphere.dto.request.ProfileUpdateRequest;
import tn.esprit.fundsphere.dto.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private IUserService iuserService;
    @Autowired
    private UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        User user = iuserService.getUserByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("address", user.getAddress());
        response.put("phoneNumber", user.getPhoneNumber());
        response.put("cin", user.getCin());
        response.put("datedenaissance", user.getDatedenaissance());
        response.put("role", user.getRole().getName().name());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        iuserService.updateUserProfile(email, profileUpdateRequest);
        return ResponseEntity.ok(new MessageResponse("User profile updated successfully"));
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean success = iuserService.changePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok().body(new ResponseMessage("Password changed successfully"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage("Invalid email or password"));
        }
    }
    public static class ResponseMessage {
        private String message;
        public ResponseMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = iuserService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = iuserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication) {
        String email = "";
        System.out.println("***************************"+authentication.getAuthorities());
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            email = userDetails.getEmail();
        }
        try {
            iuserService.deleteUser(id, email);
            return ResponseEntity.ok().body(new MessageResponse("User deleted successfully"));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> searchUsers(@RequestParam(value = "searchTerm", required = false) String searchTerm) {
        List<User> users = iuserService.searchUsers(searchTerm);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/current")
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        User currentUser = iuserService.getUserByEmail(email);
        System.out.println("User Portfolio: " + currentUser.getPortefeuille());

        return currentUser;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = iuserService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/exists/{id}")

    public boolean userExist(@PathVariable Long id)
    {
        return iuserService.existsById(id);
    }
    @PostMapping("/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> validateAccounts(@RequestBody AccountValidationRequest request, Authentication authentication) {
        Optional<User> userOptional = iuserService.findById(request.getUserId());

        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Error: User not found.");
        }
        String email = "";
        System.out.println("***************************"+authentication);
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            System.out.println("***************************°°°°°°°°°°°°°°°°°°°°°"+userDetails);

            email = userDetails.getEmail();
        }
        try {
            User requester = iuserService.getUserByEmail(email);
            ERole requesterRole = requester.getRole().getName();
            if (requesterRole.equals(ERole.ADMIN)) {
                User user = userOptional.get();
                user.setStatus(request.getStatus());
                iuserService.save(user);
                return ResponseEntity.ok(user);

            } else {
                throw new RuntimeException("You do not have permission to update the Status of this user.");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
}
