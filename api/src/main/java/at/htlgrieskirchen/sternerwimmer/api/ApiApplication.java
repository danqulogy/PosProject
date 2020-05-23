package at.htlgrieskirchen.sternerwimmer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class ApiApplication {
    @Autowired
    private TableRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }


}
