package tn.esprit.fundsphere.utils;

import tn.esprit.fundsphere.Services.UserService.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {
    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${application.security.jwt.expiration}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            //Récupère les détails de l'utilisateur authentifié qui sont conserver dans l'objet authentication
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userPrincipal.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());

            return Jwts.builder()
                    .setSubject((userPrincipal.getEmail())) //  getEmail() thabet("subject" du token comme l'email de l'utilisateur)
                    .claim("roles", roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key(), SignatureAlgorithm.HS256)
                    .compact();
        } else {
            throw new IllegalArgumentException("Authentication principal is not an instance of UserDetailsImpl");
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRolesFromJwtToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            List<String> roles = claimsJws.getBody().get("roles", List.class);
            if (roles != null) {
                return roles;
            }
        } catch (Exception e) {
            log.error("Error extracting roles from JWT token: {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    public Long extractUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userId", Long.class);  // Assurez-vous que "userId" est le nom du claim
        } catch (Exception e) {
            throw new RuntimeException("Token JWT invalide ou expiré");
        }
    }
}
