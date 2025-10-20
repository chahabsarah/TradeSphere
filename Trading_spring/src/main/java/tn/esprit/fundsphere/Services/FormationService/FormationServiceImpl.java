package tn.esprit.fundsphere.Services.FormationService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.FormationManagement.Formation;
import tn.esprit.fundsphere.Repositories.FormationRepository.FormationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FormationServiceImpl implements IFormationService {
    private FormationRepository formationRepository;
    @Override
    public Formation addFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public void deleteFormation(Long idFormation) {
        formationRepository.deleteById(idFormation);

    }

    @Override
    public Formation updateFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();    }

    @Override
    public Formation getFormation(Long idFormation) {
        return formationRepository.findById(idFormation).get();
    }

}
