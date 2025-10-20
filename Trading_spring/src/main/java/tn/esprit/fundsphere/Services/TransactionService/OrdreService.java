package tn.esprit.fundsphere.Services.TransactionService;



import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;

import java.util.List;
import java.util.Optional;

public interface OrdreService {
    List<Ordre> getAllOrdres();
    Optional<Ordre> getOrdreById(Long id);
    Ordre updateOrdre(Long id, Ordre ordre);
    void deleteOrdre(Long id);
    Ordre createorder(Ordre ordre);
}