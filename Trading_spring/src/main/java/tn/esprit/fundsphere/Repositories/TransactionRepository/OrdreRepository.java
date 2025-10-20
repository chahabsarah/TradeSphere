package tn.esprit.fundsphere.Repositories.TransactionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;

import java.util.List;

public interface OrdreRepository extends JpaRepository<Ordre,Long> {
    List<Ordre> findByPortefeuille_IdPortefeuille(long idPortefeuille);

}
