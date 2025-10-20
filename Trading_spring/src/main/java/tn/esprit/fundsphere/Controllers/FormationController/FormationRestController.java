package tn.esprit.fundsphere.Controllers.FormationController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.fundsphere.Entities.FormationManagement.Formation;
import tn.esprit.fundsphere.Services.FormationService.FormationServiceImpl;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/formation")
@CrossOrigin(origins = "http://localhost:4200")
public class FormationRestController {
    public FormationServiceImpl formationService ;

    @PostMapping(path = "/add-formation")
    public Formation addFormation (@RequestBody Formation formation)
    {
        return formationService.addFormation(formation);
    }
    @GetMapping("/show-Formations")
    public List<Formation> getAllFormations() {
        List<Formation> listFormations = formationService.getAllFormations();
        return listFormations;
    }
    @DeleteMapping(path = "/delete-formation/{id}")
    public void deleteFormation (@PathVariable ("id") Long idFormation)
    {
        formationService.deleteFormation(idFormation);
    }


    @PutMapping(path = "/update-formation")
    public Formation updateFormation(@RequestBody Formation fr)
    {

        Formation formation = formationService.updateFormation( fr);
        return formation ;
    }
}

