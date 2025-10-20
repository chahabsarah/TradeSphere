package tn.esprit.fundsphere.Entities.UserManagment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long SecurityID;
    private String sharedData;
    private Boolean isActivated;

}
