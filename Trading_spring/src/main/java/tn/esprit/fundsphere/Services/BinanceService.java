package tn.esprit.fundsphere.Services;

import com.binance.connector.client.enums.HttpMethod;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.RequestHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;

import java.util.LinkedHashMap;
@Service
public class BinanceService {
    private final SpotClientImpl client;

    @Value("${binance.api.key}")
    private String apiKey;

    @Value("${binance.api.secret}")
    private String secretKey;

    @Value("${payme.api.key}")
    private String apiKeyPayMe;

    @Value("${payme.api.url}")
    private String apiUrlPayMe;


    public BinanceService() {
        client = new SpotClientImpl(apiKey, secretKey);
    }

    public double getConversionRate(String fromSymbol, String toSymbol) throws Exception {
        String endpoint = "/api/v3/ticker/price";
        String symbol = fromSymbol + toSymbol;

        if (symbol.isEmpty() || symbol.length() < 6) {
            throw new IllegalArgumentException("Les symboles doivent être valides, comme ETHUSDT.");
        }
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);

        RequestHandler handler = new RequestHandler(
                "vvChur54UGQGRLdeBwbJK1vtTYmnivCxT7mnZogenv72QanfDC6roCVkJWpqSNJa",
                "4XBOdwJuSiAjYaweX5BAzbxFqVQ2jImgWYQnYBcrPfPwliFPjv9FtYavKVOuic2t"
        );

        String response = handler.sendPublicRequest(
                "https://api.binance.com",
                endpoint,
                parameters,
                com.binance.connector.client.enums.HttpMethod.GET,
                false
        );

        JSONObject jsonResponse = new JSONObject(response);
        if (!jsonResponse.has("price")) {
            throw new Exception("La réponse ne contient pas d'information sur le prix.");
        }

        return jsonResponse.getDouble("price");
    }

    // Method to send the USD equivalent amount to PayMe
    public boolean transferToPayMe(double amountInUSD, String payMeAccount) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Construire le corps de la requête
            JSONObject requestBody = new JSONObject();
            requestBody.put("amount", amountInUSD);
            requestBody.put("account", payMeAccount);

            // Configurer les en-têtes
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKeyPayMe);
            headers.set("Content-Type", "application/json");

            // Créer l'entité de la requête
            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            // Envoyer la requête POST à l'API PayMe
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrlPayMe, entity, String.class);

            // Vérifier si la requête a réussi
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
