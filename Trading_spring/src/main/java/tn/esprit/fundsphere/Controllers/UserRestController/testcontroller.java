package tn.esprit.fundsphere.Controllers.UserRestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testcontroller {
    @GetMapping("/check")
    public String healthCheck() {
        System.out.println("Health Check Endpoint Invoked");
        return "OK";
    }
}
