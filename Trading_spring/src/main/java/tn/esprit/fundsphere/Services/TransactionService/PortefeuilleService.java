package tn.esprit.fundsphere.Services.TransactionService;


import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;

import java.util.List;
import java.util.Optional;

public interface PortefeuilleService {
    List<Portefeuille> getAllPortefeuilles();

    Optional<Portefeuille> getPortefeuilleById(Long id);

    Portefeuille createPortefeuille(Portefeuille portefeuille);

    Portefeuille updatePortefeuille(Long id, Portefeuille portefeuille);

    void deletePortefeuille(Long id);

    Portefeuille getPortefeuilleByEncryptedWalletId(String encryptedWalletId);
}
