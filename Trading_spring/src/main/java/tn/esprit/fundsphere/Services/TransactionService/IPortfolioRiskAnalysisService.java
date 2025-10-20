package tn.esprit.fundsphere.Services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;
import tn.esprit.fundsphere.Repositories.TransactionRepository.PortefeuilleRepository;

import java.util.List;
import java.util.Optional;

public interface IPortfolioRiskAnalysisService {

    double calculatePortfolioVaR(List<Ordre> orders, double confidenceLevel);
   Portefeuille getPortefeuilleByEncryptedWalletId(String encryptedWalletId);

}
