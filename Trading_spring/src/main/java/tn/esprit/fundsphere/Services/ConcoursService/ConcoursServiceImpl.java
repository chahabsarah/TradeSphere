package tn.esprit.fundsphere.Services.ConcoursService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.FormationManagement.Concours;
import tn.esprit.fundsphere.Repositories.FormationRepository.ConcoursRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class ConcoursServiceImpl implements IConcoursService {
    private ConcoursRepository concoursRepository;
    @Override
    public Concours addConcours(Concours concours) {
        return concoursRepository.save(concours);
    }

    @Override
    public void deleteConcours(Long idConcours) {
        concoursRepository.deleteById(idConcours);

    }

    @Override
    public Concours updateConcours(Concours concours) {
        return concoursRepository.save(concours);
    }

    @Override
    public List<Concours> getAllConcours() {
        return concoursRepository.findAll();    }

    @Override
    public Concours getConcour(Long idConcours) {
        return concoursRepository.findById(idConcours).get();
    }

}
