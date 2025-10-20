package tn.esprit.fundsphere.Repositories.TransactionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;

import java.util.Optional;

public interface PortefeuilleRepository extends JpaRepository<Portefeuille,Long> {
   Portefeuille findByEncryptedWalletId(String encryptedWalletId);

}
