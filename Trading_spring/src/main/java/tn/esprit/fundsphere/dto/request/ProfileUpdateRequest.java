package tn.esprit.fundsphere.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ProfileUpdateRequest {

    private String username;
    private String address;
    private String phoneNumber;
    private Long cin;
    private LocalDate datedenaissance;


}
