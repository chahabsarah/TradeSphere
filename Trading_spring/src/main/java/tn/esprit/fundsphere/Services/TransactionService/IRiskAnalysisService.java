package tn.esprit.fundsphere.Services.TransactionService;

import java.util.List;

public interface IRiskAnalysisService {
    double calculateVaR(List<Double> returns, double confidenceLevel);
}
