package tn.esprit.fundsphere.Entities.TransactionManagement;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ordre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID
    Long idOrdre;

    // Type d'ordre (achat ou vente, par exemple)
    @Enumerated(EnumType.STRING)
    TypeOrdre typeOrdre;

    // Date de création de l'ordre
    @Temporal(TemporalType.TIMESTAMP) // Assure le bon format de la date
            Date dateOrdre;

    // Prix unitaire de l'actif lors de l'ordre
    float unitPrice;

    // Statut de l'ordre (exécuté ou non)
    boolean status;

    int quantity;


    // Type de l'actif acheté/vendu
    @Enumerated(EnumType.STRING)
    Actif actif;

    @ManyToOne
    @JoinColumn(name = "portefeuille_id", nullable = false) // Colonne pour la clé étrangère
    @JsonBackReference
    private Portefeuille portefeuille;



    // Initialiser la date d'ordre avant la persistance
    @PrePersist
    public void prePersist() {
        this.dateOrdre = new Date(); // Initialise la date d'ordre avec la date actuelle
    }



}
