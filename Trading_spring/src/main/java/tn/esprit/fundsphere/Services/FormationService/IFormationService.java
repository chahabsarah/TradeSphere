package tn.esprit.fundsphere.Services.FormationService;

import tn.esprit.fundsphere.Entities.FormationManagement.Formation;

import java.util.List;

public interface IFormationService {
    Formation addFormation(Formation formation);

    void deleteFormation(Long idFormation);

    Formation updateFormation(Formation formation);

    List<Formation> getAllFormations();

    Formation getFormation(Long idFormation);
}
