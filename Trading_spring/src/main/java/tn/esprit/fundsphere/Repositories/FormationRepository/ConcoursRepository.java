package tn.esprit.fundsphere.Repositories.FormationRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fundsphere.Entities.FormationManagement.Concours;

public interface ConcoursRepository extends JpaRepository<Concours,Long> {
}
