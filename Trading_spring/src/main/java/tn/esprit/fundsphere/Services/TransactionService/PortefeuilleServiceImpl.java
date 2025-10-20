package tn.esprit.fundsphere.Services.TransactionService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;
import tn.esprit.fundsphere.Repositories.TransactionRepository.PortefeuilleRepository;

import java.util.List;
import java.util.Optional;
@Service

public class PortefeuilleServiceImpl implements PortefeuilleService {

    @Autowired
   private
    PortefeuilleRepository portefeuilleRepository;

    @Override
    public List<Portefeuille> getAllPortefeuilles() {
        return portefeuilleRepository.findAll();
    }

    @Override
    public Optional<Portefeuille> getPortefeuilleById(Long id) {
        return portefeuilleRepository.findById(id);
    }

    @Override
    public Portefeuille createPortefeuille(Portefeuille portefeuille) {
        return portefeuilleRepository.save(portefeuille);
    }


    @Override
    @Transactional
    public Portefeuille updatePortefeuille(Long id, Portefeuille updatedPortefeuille) {
        Optional<Portefeuille> existingPortefeuilleOpt = portefeuilleRepository.findById(id);

        if (existingPortefeuilleOpt.isPresent()) {
            Portefeuille existingPortefeuille = existingPortefeuilleOpt.get();

            // Mettre à jour les informations de base du portefeuille
            existingPortefeuille.setTotalValue(updatedPortefeuille.getTotalValue());
            existingPortefeuille.setLiquidity(updatedPortefeuille.getLiquidity());

            // Mettre à jour les ordres du portefeuille
            List<Ordre> existingOrdres = existingPortefeuille.getOrdres();
            existingOrdres.clear(); // Effacer les ordres existants avant de les remplacer
            existingOrdres.addAll(updatedPortefeuille.getOrdres());

            // Mettre à jour la relation de portefeuille pour chaque nouvel ordre
            for (Ordre ordre : existingOrdres) {
                ordre.setPortefeuille(existingPortefeuille); // Associer chaque ordre au portefeuille
            }

            // Sauvegarder et retourner le portefeuille mis à jour
            return portefeuilleRepository.save(existingPortefeuille);
        } else {
            return null; // Si le portefeuille n'existe pas
        }
    }

    @Override
    public void deletePortefeuille(Long id) {
        portefeuilleRepository.deleteById(id);
    }
    @Override
    public Portefeuille getPortefeuilleByEncryptedWalletId(String encryptedWalletId) {
        // Implémentation pour obtenir un portefeuille par encryptedWalletId
        return portefeuilleRepository.findByEncryptedWalletId(encryptedWalletId);
    }
}
