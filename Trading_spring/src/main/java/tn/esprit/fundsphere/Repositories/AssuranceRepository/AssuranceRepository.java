package tn.esprit.fundsphere.Repositories.AssuranceRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fundsphere.Entities.AssuranceManagement.Contract;


public interface AssuranceRepository extends JpaRepository<Contract,Long> {
}
