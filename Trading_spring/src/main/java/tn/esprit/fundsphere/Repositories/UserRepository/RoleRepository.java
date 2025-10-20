package tn.esprit.fundsphere.Repositories.UserRepository;


import tn.esprit.fundsphere.Entities.UserManagment.ERole;
import tn.esprit.fundsphere.Entities.UserManagment.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);


}
