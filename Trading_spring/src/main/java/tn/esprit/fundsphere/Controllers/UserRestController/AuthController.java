package tn.esprit.fundsphere.Controllers.UserRestController;

import tn.esprit.fundsphere.Entities.UserManagment.ERole;
import tn.esprit.fundsphere.Entities.UserManagment.Role;
import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.Entities.UserManagment.UserStatus;
import tn.esprit.fundsphere.Repositories.UserRepository.RoleRepository;
import tn.esprit.fundsphere.Repositories.UserRepository.UserRepository;
import tn.esprit.fundsphere.Services.UserService.OtpService;
import tn.esprit.fundsphere.Services.UserService.UserDetailsImpl;
import tn.esprit.fundsphere.dto.request.LoginRequest;
import tn.esprit.fundsphere.dto.request.OtpRequest;
import tn.esprit.fundsphere.dto.request.SignupRequest;
import tn.esprit.fundsphere.dto.response.JwtResponse;
import tn.esprit.fundsphere.dto.response.MessageResponse;
import tn.esprit.fundsphere.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static tn.esprit.fundsphere.Entities.UserManagment.UserStatus.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private OtpService otpService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        // Check if email is already in use
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Validate email domain
        if (!signUpRequest.getEmail().contains("@gmail.com") && !signUpRequest.getEmail().contains("@esprit.tn")) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is out of domain!"));
        }

        // Create user object from SignupRequest
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getAddress(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getCin(),
                signUpRequest.getDatedenaissance()
        );

        // Fetch and set role from the request
        Role role = roleRepository.findById(signUpRequest.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        user.setRole(role);

        // Set user status based on role and email domain
        if (role.getName().equals(ERole.CLIENT)) {
            user.setStatus(UserStatus.PENDING);
        } else if (role.getName().equals(ERole.ADMIN) && signUpRequest.getEmail().contains("@esprit.tn")) {
            user.setStatus(UserStatus.ACTIVE);
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role out of domain"));
        }

        // Save the user in the repository
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        // Check if user exists and if the status is active (not pending)
        if (user == null || user.getStatus() == PENDING) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid credentials or account pending"));
        }

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Send OTP to user
        otpService.sendOtpToUser(user);

        return ResponseEntity.ok(new MessageResponse("OTP sent to your registered email"));
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
        // Find user by email
        User user = userRepository.findByEmail(otpRequest.getEmail()).orElse(null);

        // Validate OTP
        if (user == null || user.getOtp() == null || !user.getOtp().equals(otpRequest.getOtp()) || user.getOtpExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid or expired OTP"));
        }

        // Clear OTP after successful verification
        user.setOtp(null);
        user.setOtpExpiryTime(null);
        userRepository.save(user);

        // Re-authenticate user for JWT generation
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), otpRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Extract user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Create and return JWT response
        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAddress(),
                userDetails.getPhoneNumber(),
                userDetails.getCin(),
                userDetails.getSituationfamiliale(),
                userDetails.getDatedenaissance(),
                userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(jwtResponse);
    }
}
