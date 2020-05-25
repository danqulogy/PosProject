package at.htlgrieskirchen.sternerwimmer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {
    @Autowired
    private TableRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        restaurantRepository.deleteAll();
        restaurantRepository.save(new Restaurant("1","13.9559","48.2054", "Oberndorfer"));
        restaurantRepository.save(new Restaurant("2", "88", "50", "Hofwirt"));
    }
}
