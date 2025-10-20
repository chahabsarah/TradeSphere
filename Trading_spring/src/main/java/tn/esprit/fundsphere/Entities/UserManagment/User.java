package tn.esprit.fundsphere.Entities.UserManagment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String address;

    private String phoneNumber;

    private Long cin;

    private LocalDate datedenaissance;


    private String otp;
    private LocalDateTime otpExpiryTime;



    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "portefeuille_id", referencedColumnName = "idPortefeuille")
    private Portefeuille portefeuille;

    public User(String username, String email, String password, String address, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public User(String username, String email, String password, String address, String phoneNumber,Long cin,LocalDate datedenaissance) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cin= cin;
        this.datedenaissance = datedenaissance;
    }
}
