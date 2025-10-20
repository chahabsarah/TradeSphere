package tn.esprit.fundsphere.Entities.UserManagment;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    // Method to create Role from a string (roleName)
    public static Role fromString(String roleName) {
        ERole role = ERole.valueOf(roleName.toUpperCase());
        if (role.equals("ADMIN")){
            return new Role(0, role);}
        return new Role(1, role);
    }
}
