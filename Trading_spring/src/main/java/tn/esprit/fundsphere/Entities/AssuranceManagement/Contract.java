package tn.esprit.fundsphere.Entities.AssuranceManagement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;
import tn.esprit.fundsphere.Entities.UserManagment.User;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contract {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int idContract ;
    float coverage ;
    float prime ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date endDate;

    @Enumerated(EnumType.STRING)
    Policy policy;

    @ManyToOne
    @JsonIgnore
    private User user;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portefeuille_id")
    private Portefeuille portefeuille;

}
