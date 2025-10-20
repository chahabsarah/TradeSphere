package tn.esprit.fundsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FundsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundsphereApplication.class, args);
    }



}
