package tn.esprit.fundsphere.Controllers.TransactionController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tn.esprit.fundsphere.Entities.TransactionManagement.Portefeuille;
import tn.esprit.fundsphere.Entities.UserManagment.User;
import tn.esprit.fundsphere.Services.BinanceService;
import tn.esprit.fundsphere.Services.TransactionService.PortefeuilleService;
import tn.esprit.fundsphere.Services.UserService.IUserService;
import tn.esprit.fundsphere.Services.UserService.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/portfeuilles")
public class PortefeuilleController {
    @Autowired
    private PortefeuilleService portfeuilleService;
    private IUserService userService;

    @GetMapping
    public List<Portefeuille> getAllPortefeuilles() {
        return portfeuilleService.getAllPortefeuilles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portefeuille> getPortefeuilleById(@PathVariable Long id) {
        Optional<Portefeuille> portefeuille = portfeuilleService.getPortefeuilleById(id);
        return portefeuille.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Autowired
    private IUserService iuserService;
    @PostMapping
    public ResponseEntity<Portefeuille> createPortefeuille(@RequestBody Portefeuille portefeuille, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        System.out.println("email: "+ email);
        User currentUser = iuserService.getUserByEmail(email);
        User user = iuserService.getUserById(currentUser.getId());
        System.out.println("User: "+ user);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        portefeuille.setUser(user);
        Portefeuille newPortefeuille = portfeuilleService.createPortefeuille(portefeuille);
        user.setPortefeuille(newPortefeuille);
        iuserService.save(user);
        return ResponseEntity.ok(newPortefeuille);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Portefeuille> updatePortefeuille(@PathVariable Long id, @RequestBody Portefeuille portefeuille) {
        Portefeuille updatedPortefeuille = portfeuilleService.updatePortefeuille(id, portefeuille);
        if (updatedPortefeuille != null) {
            return ResponseEntity.ok(updatedPortefeuille);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortefeuille(@PathVariable Long id) {
        portfeuilleService.deletePortefeuille(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/envoyer")
    public ResponseEntity<String> envoyerDollar(@RequestBody TransactionRequest transactionRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        User currentUser = iuserService.getUserByEmail(email);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }
        Portefeuille portefeuilleExpediteur = currentUser.getPortefeuille();
        Portefeuille portefeuilleDestinataire = portfeuilleService.getPortefeuilleByEncryptedWalletId(transactionRequest.getEncryptedWalletId());
        if (portefeuilleDestinataire == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Portefeuille destinataire non trouvé");
        }
        if (portefeuilleExpediteur.getLiquidity() < transactionRequest.getAmount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solde insuffisant");
        }
        portefeuilleExpediteur.setLiquidity(portefeuilleExpediteur.getLiquidity() - transactionRequest.getAmount());
        portefeuilleExpediteur.setTotalValue(portefeuilleExpediteur.getTotalValue() - transactionRequest.getAmount());
        portefeuilleDestinataire.setLiquidity(portefeuilleDestinataire.getLiquidity() + transactionRequest.getAmount());
        portefeuilleDestinataire.setTotalValue(portefeuilleDestinataire.getTotalValue() + transactionRequest.getAmount());
        portfeuilleService.createPortefeuille(portefeuilleExpediteur);
        portfeuilleService.createPortefeuille(portefeuilleDestinataire);
        return ResponseEntity.ok("Transaction effectuée avec succès");
    }

    @PostMapping("/retrait")
    public ResponseEntity<String> retraitDollar(@RequestBody TransactionRequest transactionRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        User currentUser = iuserService.getUserByEmail(email);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        Portefeuille portefeuille = currentUser.getPortefeuille();
        if (portefeuille.getLiquidity() < transactionRequest.getAmount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solde insuffisant");
        }

        String cryptoCurrency = transactionRequest.getCryptoCurrency().toUpperCase();
        if (!cryptoCurrency.equals("BTC") && !cryptoCurrency.equals("ETH") && !cryptoCurrency.equals("BNB")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Crypto-monnaie non valide");
        }

        BinanceService binanceService = new BinanceService();

        double conversionRate;
        try {
            conversionRate = binanceService.getConversionRate(cryptoCurrency, "USDT");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération du taux de conversion.");
        }

        if (conversionRate <= 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Taux de conversion non valide.");
        }

        double amountInUSD = transactionRequest.getAmount() * conversionRate;
        System.out.println("USD Amount: " + amountInUSD);

        // Transférer l'argent à PayMe
        try {
            boolean transferSuccess = binanceService.transferToPayMe(amountInUSD, transactionRequest.getPayMeAccount());
            System.out.println("err " );

            if (!transferSuccess) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du transfert vers PayMe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'exécution du transfert.");
        }

        portefeuille.setLiquidity(portefeuille.getLiquidity() - transactionRequest.getAmount());
        portefeuille.setTotalValue(portefeuille.getTotalValue() - amountInUSD);
        portfeuilleService.createPortefeuille(portefeuille);

        return ResponseEntity.ok("Retrait effectué avec succès. Montant transféré : " + amountInUSD + " USD.");
    }

    }

class TransactionRequest {
    private String encryptedWalletId;
    private double amount;
    private String cryptoCurrency;
    private String payMeAccount;

    // Getter and Setter methods
    public String getEncryptedWalletId() {
        return encryptedWalletId;
    }

    public void setEncryptedWalletId(String encryptedWalletId) {
        this.encryptedWalletId = encryptedWalletId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(String cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public String getPayMeAccount() {
        return payMeAccount;
    }

    public void setPayMeAccount(String payMeAccount) {
        this.payMeAccount = payMeAccount;
    }
}
