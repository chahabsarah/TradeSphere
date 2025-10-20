package tn.esprit.fundsphere.Services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;

import java.util.List;

@Service
public class PortfolioRiskAnalysisService implements IPortfolioRiskAnalysisService {
    private final IRiskAnalysisService  riskAnalysisService ;
    private final  IMarketDataSimulator marketDataSimulator ;

    @Autowired
    public PortfolioRiskAnalysisService(RiskAnalysisService riskAnalysisService, MarketDataSimulator marketDataSimulator) {
        this.riskAnalysisService = riskAnalysisService;
        this.marketDataSimulator = marketDataSimulator;
    }

    @Override
    public double calculatePortfolioVaR(List<Ordre> orders, double confidenceLevel) {
        double totalPortfolioValue = calculatePortfolioValue(orders);
        double portfolioVaR = 0;

        // Simuler des rendements pour chaque actif dans le portefeuille
        for (Ordre ordre : orders) {
            List<Double> returns = marketDataSimulator.simulateReturns(30);  // Simuler 30 jours de rendements
            double assetVaR = riskAnalysisService.calculateVaR(returns, confidenceLevel);
            portfolioVaR += assetVaR * (ordre.getQuantity() * ordre.getUnitPrice() / totalPortfolioValue); // Pondérer par la valeur de l'actif
        }

        return portfolioVaR;
    }

    @Override
    public Portefeuille getPortefeuilleByEncryptedWalletId(String encryptedWalletId) {
        return null;
    }

    // Calculer la valeur totale du portefeuille
    private double calculatePortfolioValue(List<Ordre> orders) {
        return orders.stream()
                .mapToDouble(ordre -> ordre.getQuantity() * ordre.getUnitPrice())
                .sum();
    }
}
