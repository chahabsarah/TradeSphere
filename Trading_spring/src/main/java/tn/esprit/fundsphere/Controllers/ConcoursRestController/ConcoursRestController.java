package tn.esprit.fundsphere.Controllers.ConcoursRestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.fundsphere.Entities.FormationManagement.Concours;
import tn.esprit.fundsphere.Services.ConcoursService.ConcoursServiceImpl;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Concours ")
public class ConcoursRestController {
    public ConcoursServiceImpl concoursService ;
    @PostMapping(path = "/add-Concours")
    public Concours addConcours (@RequestBody Concours concours)
    {
        return concoursService.addConcours(concours);
    }
    @GetMapping("/show-Concours")
    public List<Concours> getAllConcours() {
        List<Concours> listConcours = concoursService.getAllConcours();
        return listConcours ;
    }
    @DeleteMapping(path = "/delete-concours/{id}")
    public void deleteConcours (@PathVariable ("id") Long idConcours)
    {
        concoursService.deleteConcours(idConcours);
    }


    @PutMapping(path = "/update-concours")
    public Concours updateConcours(@RequestBody Concours co)
    {

        Concours concours = concoursService.updateConcours( co);
        return concours ;
    }
}
