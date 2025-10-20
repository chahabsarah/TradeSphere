
package tn.esprit.fundsphere.Services.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("tektaitheoriginals@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);

        // Set HTML content with inline styles and embedded images
        String htmlContent = "<html>" +
                "<body>" +
                "<table width='100%' cellpadding='0' cellspacing='0' border='0'>" +
                "<tr>" +
                "<td style='background-color:#e9eff2;  padding:20px; border-radius:20px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);'>" +
                "<table width='600px' cellpadding='0' cellspacing='0' border='0' align='center'>" +
                "<tr>" +
                "<td style='text-align:center; padding:20px;'>" +
                "<img src='cid:logoImage' alt='Logo' style='width:200px;height:200px;'/>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='text-align:center; color:white;'>" + // Text color
                "<p>" + body + "</p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        helper.setText(htmlContent, true);

        // Attach the logo image
//        ClassPathResource resource = new ClassPathResource("static/gti.png"); // Path to your logo image
//        helper.addInline("logoImage", resource);

        // Sending the email
        mailSender.send(mimeMessage);
        System.out.println("Mail Sent...");
    }
}
