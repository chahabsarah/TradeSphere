package tn.esprit.fundsphere.Entities.TransactionManagement;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.fundsphere.Entities.AssuranceManagement.Contract;
import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.utils.EncryptionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Portefeuille {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long idPortefeuille;

    @Column(nullable = false, unique = true)
    String encryptedWalletId;

    double totalValue;

    double liquidity;
    @PrePersist
    private void generateAndEncryptWalletId() {
        try {
            // Générer un UUID comme walletId
            String walletId = UUID.randomUUID().toString();
            // Chiffrer le walletId avant de le stocker
            this.encryptedWalletId = EncryptionUtil.encrypt(walletId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement du walletId", e);
        }
    }

    public String getDecryptedWalletId() {
        try {
            return EncryptionUtil.decrypt(this.encryptedWalletId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement du walletId", e);
        }
    }
    @JsonManagedReference
    @OneToMany(mappedBy = "portefeuille", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ordre> ordres = new ArrayList<>();

    @OneToMany(mappedBy = "portefeuille",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Contract> contracts;


    @OneToOne(mappedBy="portefeuille" ,fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private User user;



}
