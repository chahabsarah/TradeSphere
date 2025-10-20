package tn.esprit.fundsphere.Controllers.TransactionController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/prediction")
public class PredictionController {

    @GetMapping
    public ResponseEntity<String> getPrediction() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "http://localhost:8889/notebooks/trading/Untitled.ipynb");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor(); // Attendre que le processus se termine
            return ResponseEntity.ok(output.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'exécution du script Python: " + e.getMessage());
        }
    }
}
