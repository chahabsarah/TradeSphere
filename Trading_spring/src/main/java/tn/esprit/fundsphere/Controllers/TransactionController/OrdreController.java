package tn.esprit.fundsphere.Controllers.TransactionController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;
import tn.esprit.fundsphere.Services.TransactionService.OrdreService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordres") // URL de base pour ce contrôleur
public class OrdreController {

    @Autowired
    private OrdreService ordreService;

    // GET: /api/ordres
    @GetMapping
    public List<Ordre> getAllOrdres() {
        return ordreService.getAllOrdres();
    }

    // GET: /api/ordres/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ordre> getOrdreById(@PathVariable Long id) {
        Optional<Ordre> ordre = ordreService.getOrdreById(id);
        return ordre.map(ResponseEntity::ok) // Retourne 200 OK si trouvé
                .orElseGet(() -> ResponseEntity.notFound().build()); // Retourne 404 si non trouvé
    }



    // PUT: /api/ordres/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Ordre> updateOrdre(@PathVariable Long id, @RequestBody Ordre ordre) {
        Ordre updatedOrdre = ordreService.updateOrdre(id, ordre);
        return updatedOrdre != null ? ResponseEntity.ok(updatedOrdre) // Retourne 200 OK si mis à jour
                : ResponseEntity.notFound().build(); // Retourne 404 si non trouvé
    }

    // DELETE: /api/ordres/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdre(@PathVariable Long id) {
        ordreService.deleteOrdre(id);
        return ResponseEntity.noContent().build(); // Retourne 204 No Content
    }

    // POST: /api/ordres/transaction
    @PostMapping("/createorder")
    public ResponseEntity<Ordre> createorder(@RequestBody Ordre ordre) {
        Ordre executedOrdre = ordreService.createorder(ordre);
        return executedOrdre != null ? ResponseEntity.ok(executedOrdre) // Retourne 200 OK si la transaction réussie
                : ResponseEntity.badRequest().build(); // Retourne 400 si la transaction échoue
    }
}
