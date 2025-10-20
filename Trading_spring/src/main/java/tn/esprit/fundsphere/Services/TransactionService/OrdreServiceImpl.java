package tn.esprit.fundsphere.Services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;
import tn.esprit.fundsphere.Entities.TransactionManagement.TypeOrdre;
import tn.esprit.fundsphere.Repositories.TransactionRepository.OrdreRepository;
import tn.esprit.fundsphere.Repositories.TransactionRepository.PortefeuilleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrdreServiceImpl implements OrdreService {
    @Autowired
    private OrdreRepository ordreRepository;

    @Autowired
    private PortefeuilleRepository portefeuilleRepository; // Ajout de @Autowired ici

    @Override
    public List<Ordre> getAllOrdres() {
        return ordreRepository.findAll();
    }

    @Override
    public Optional<Ordre> getOrdreById(Long id) {
        return ordreRepository.findById(id);
    }

    @Autowired
    private EmailService emailService;

    @Override
    public Ordre updateOrdre(Long id, Ordre ordre) {
        if (ordreRepository.existsById(id)) {
            ordre.setIdOrdre(id);
            return ordreRepository.save(ordre);
        }
        return null; // Ou lancez une exception si l'ordre n'existe pas
    }

    @Override
    public void deleteOrdre(Long id) {
        ordreRepository.deleteById(id);
    }

    @Override
    public Ordre createorder(Ordre ordre) {
        // Récupérer le portefeuille associé à l'ordre
        Portefeuille portefeuille = portefeuilleRepository.findById(ordre.getPortefeuille().getIdPortefeuille())
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé"));

        double liquidity = portefeuille.getLiquidity();
        double ordrePrice = ordre.getUnitPrice(); // Prix unitaire de l'actif

        // Vérifier le type d'ordre
        if (ordre.getTypeOrdre() == TypeOrdre.BUY) {
            // Vérifier si la liquidité du portefeuille est suffisante pour l'achat
            if (liquidity >= ordrePrice) {
                // Effectuer la transaction
                portefeuille.setLiquidity(liquidity - ordrePrice);
                portefeuille.setTotalValue(portefeuille.getTotalValue() - ordrePrice);
                ordre.setStatus(true); // Marquer l'ordre comme exécuté
            } else {
                // Si la liquidité est insuffisante, ne pas exécuter l'ordre
                ordre.setStatus(false);
                System.out.println("Liquidité insuffisante pour effectuer cet achat.");
                return ordre; // Retourner l'ordre avec le statut
            }
        } else if (ordre.getTypeOrdre() == TypeOrdre.SELL) {
            // Effectuer la vente, ajouter la somme à la liquidité
            portefeuille.setLiquidity(liquidity + ordrePrice);
            portefeuille.setTotalValue(portefeuille.getTotalValue() + ordrePrice);
            ordre.setStatus(true); // Marquer l'ordre comme exécuté
        }

        // Sauvegarder les modifications du portefeuille
        portefeuilleRepository.save(portefeuille);

        // Sauvegarder l'ordre
        Ordre savedOrdre = ordreRepository.save(ordre);

        // Send an email notification to the concerned user
        String userEmail = portefeuille.getUser().getEmail(); // Assuming the user email is stored in the Portefeuille
        String subject = "Order Confirmation";
        String text = String.format("Your order for %s has been successfully created! Order ID: %d",
                ordre.getTypeOrdre(),
                savedOrdre.getIdOrdre());
        emailService.sendSimpleMessage(userEmail, subject, text);

        return savedOrdre;
    }


}
