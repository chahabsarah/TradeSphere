package tn.esprit.fundsphere.Services.TransactionService;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RiskAnalysisService implements IRiskAnalysisService {
    @Override
    public double calculateVaR(List<Double> returns, double confidenceLevel) {
        // Trier les rendements
        Collections.sort(returns);

        // Calculer l'indice pour obtenir la VaR (quantile inférieur)
        int index = (int) ((1 - confidenceLevel) * returns.size());

        // Retourner la VaR correspondante
        return returns.get(index);
    }
}
