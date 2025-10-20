package tn.esprit.fundsphere.Services.AssuranceService;

import tn.esprit.fundsphere.Entities.AssuranceManagement.Contract;

import java.util.List;

public interface IAssuranceService {

    public Contract addContract(Contract contract);
    public void deleteContract(Long idContract);
    public Contract updateContract(Contract contract);
    public List<Contract> getAllContracts();
    public Contract getContract(Long idContract) ;
    public double calculateContractPrime(int idPortefeuille, double confidenceLevel);
    }




