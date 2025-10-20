package tn.esprit.fundsphere.Services.TransactionService;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
