package tn.esprit.fundsphere.Services.AssuranceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.fundsphere.Entities.AssuranceManagement.BacktestResult;
import tn.esprit.fundsphere.Entities.TransactionManagement.Ordre;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;
import tn.esprit.fundsphere.Entities.TransactionManagement.TypeOrdre;
import tn.esprit.fundsphere.Repositories.TransactionRepository.PortefeuilleRepository;

@Service
public class BacktestingService implements IBacktestingService {

    @Autowired
    private PortefeuilleRepository portefeuilleRepository;

    @Override
    public BacktestResult backtestStrategy(Long idPortefeuille) {
        Portefeuille portefeuille = portefeuilleRepository.findById(idPortefeuille).orElseThrow();
        double initialValue = portefeuille.getTotalValue();
        double liquidity = portefeuille.getLiquidity();
        double maxDrawdown = 0;
        double highestValue = initialValue;
        int insufficientLiquidityCount = 0;  // Compteur pour les achats refusés à cause de liquidités insuffisantes

        for (Ordre ordre : portefeuille.getOrdres()) {
            double montantTransaction = ordre.getQuantity() * ordre.getUnitPrice();

            if (ordre.getTypeOrdre() == TypeOrdre.BUY) {
                // Vérifier la liquidité avant d'acheter
                if (liquidity >= montantTransaction) {
                    portefeuille.setTotalValue(portefeuille.getTotalValue() - montantTransaction);
                    liquidity -= montantTransaction;
                } else {
                    System.out.println("Liquidité insuffisante pour l'achat. Montant requis : " + montantTransaction + ", Liquidité disponible : " + liquidity);
                    insufficientLiquidityCount++;  // Incrémenter le compteur
                    continue;
                }
            } else if (ordre.getTypeOrdre() == TypeOrdre.SELL) {
                portefeuille.setTotalValue(portefeuille.getTotalValue() + montantTransaction);
                liquidity += montantTransaction;
            }

            // Calculer le max drawdown
            if (portefeuille.getTotalValue() > highestValue) {
                highestValue = portefeuille.getTotalValue();
            }
            double drawdown = (highestValue - portefeuille.getTotalValue()) / highestValue;
            if (drawdown > maxDrawdown) {
                maxDrawdown = drawdown;
            }
        }

        double finalValue = portefeuille.getTotalValue();
        double totalReturn = finalValue - initialValue;

        // Afficher le nombre total de transactions refusées à cause de liquidités insuffisantes
        System.out.println("Nombre total d'achats refusés à cause de liquidités insuffisantes : " + insufficientLiquidityCount);

        BacktestResult result = new BacktestResult();
        result.setTotalReturn(totalReturn);
        result.setMaxDrawdown(maxDrawdown);
        result.setFinalValue(finalValue);

        return result;
    }

}

