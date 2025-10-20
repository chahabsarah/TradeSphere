package tn.esprit.fundsphere.Services.AssuranceService;

import tn.esprit.fundsphere.Entities.AssuranceManagement.BacktestResult;

public interface IBacktestingService {
     public BacktestResult backtestStrategy(Long idPortefeuille);
}