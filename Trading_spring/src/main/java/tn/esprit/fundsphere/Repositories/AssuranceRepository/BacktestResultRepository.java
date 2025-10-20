package tn.esprit.fundsphere.Repositories.AssuranceRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fundsphere.Entities.AssuranceManagement.BacktestResult;

public interface BacktestResultRepository extends JpaRepository<BacktestResult,Long> {
}
