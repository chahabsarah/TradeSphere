package tn.esprit.fundsphere.Services.UserService;

import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.Repositories.UserRepository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public String generateOtp() {
        int otp = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    public void sendOtpToUser(User user) {
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(20)); // OTP valid for 20 minutes
        userRepository.save(user);

        // Send OTP via email or SMS
        try {
            String body = "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #000000;'>" +
                    "Dear <strong>" + user.getUsername() + "</strong>," + "<br/><br/>" +
                    "Your OTP is: <strong>" + otp + "</strong></p>";

            emailService.sendEmail(user.getEmail(), "Verif Code ",body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

