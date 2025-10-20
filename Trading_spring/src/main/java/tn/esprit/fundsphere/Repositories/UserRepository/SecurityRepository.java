package tn.esprit.fundsphere.Repositories.UserRepository;

import tn.esprit.fundsphere.Entities.UserManagment.Security;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SecurityRepository extends JpaRepository<Security,Long> {
}
