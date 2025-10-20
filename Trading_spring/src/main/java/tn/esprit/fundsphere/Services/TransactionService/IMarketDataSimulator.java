package tn.esprit.fundsphere.Services.TransactionService;

import java.util.List;

public interface IMarketDataSimulator {
    List<Double> simulateReturns(int days);
}
