package tn.esprit.fundsphere.Services.AssuranceService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.AssuranceManagement.Contract;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;
import tn.esprit.fundsphere.Repositories.AssuranceRepository.AssuranceRepository;
import tn.esprit.fundsphere.Repositories.TransactionRepository.OrdreRepository;
import tn.esprit.fundsphere.Services.TransactionService.IPortfolioRiskAnalysisService;

import java.util.List;

@Service
@AllArgsConstructor
public class AssuranceServiceImpl implements IAssuranceService {

    private AssuranceRepository assuranceRepository;
    private  IPortfolioRiskAnalysisService portfolioRiskAnalysisService;
    private  OrdreRepository ordreRepository;
    private static final double TAUX_DE_PRIME = 0.02; // Taux de prime de 2%

    @Override
    public Contract addContract(Contract contract) {

        return assuranceRepository.save(contract);
    }

    @Override
    public void deleteContract(Long idContract) {
        assuranceRepository.deleteById(idContract);

    }

    @Override
    public Contract updateContract(Contract contract) {

        return assuranceRepository.save(contract);
    }

    @Override
    public List<Contract> getAllContracts() {
        return assuranceRepository.findAll();
    }

    @Override
    public Contract getContract(Long idContract) {
        return assuranceRepository.findById(idContract)
                .orElseThrow(() -> new RuntimeException("Le contrat avec ID " + idContract + " n'existe pas."));
    }
    // Calculer la prime pour un contrat basé sur le portefeuille associé
    @Override
    public double calculateContractPrime(int idPortefeuille, double confidenceLevel) {
        List<Ordre> orders = ordreRepository.findByPortefeuille_IdPortefeuille(idPortefeuille);

        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for the given portfolio ID");
        }

        // Calculer la VaR du portefeuille
        double portfolioVaR = portfolioRiskAnalysisService.calculatePortfolioVaR(orders, confidenceLevel);

        // Calculer la prime en utilisant le taux de prime et la VaR
        double prime = portfolioVaR * TAUX_DE_PRIME;

        return prime;
    }
}

