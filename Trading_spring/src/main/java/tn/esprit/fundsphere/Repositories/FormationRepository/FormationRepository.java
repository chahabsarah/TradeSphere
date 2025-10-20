package tn.esprit.fundsphere.Repositories.FormationRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fundsphere.Entities.FormationManagement.Formation;

public interface FormationRepository extends JpaRepository<Formation,Long> {
}
