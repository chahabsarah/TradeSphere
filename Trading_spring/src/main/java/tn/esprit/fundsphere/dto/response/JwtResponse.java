package tn.esprit.fundsphere.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";  // Constant value lel token

    private Long id;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    private Long cin;
    private String situationfamiliale;
    private LocalDate datedenaissance;
    private List<String> roles;


}
