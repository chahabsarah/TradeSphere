package tn.esprit.fundsphere.security.jwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component //détectée et gérée par le conteneur Spring.
public class AuthEntryPointJwt implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        // Définir le type de contenu de la réponse en JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Définir le statut de la réponse à 401 (Non autorisé)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Construire le corps de la réponse
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", "You are not authorized to access this resource.");
        body.put("path", request.getServletPath());
        // Convertir le corps de la réponse en JSON et l'écrire dans la réponse HTTP
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
