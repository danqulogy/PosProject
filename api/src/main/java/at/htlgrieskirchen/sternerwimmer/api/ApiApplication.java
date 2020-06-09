package at.htlgrieskirchen.sternerwimmer.api;

import at.htlgrieskirchen.sternerwimmer.api.classes.Reservation;
import at.htlgrieskirchen.sternerwimmer.api.classes.Restaurant;
import at.htlgrieskirchen.sternerwimmer.api.classes.RestaurantRepository;
import at.htlgrieskirchen.sternerwimmer.api.classes.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        restaurantRepository.deleteAll();
        List<Table> oberndorferTables = new ArrayList<>();
        List<Reservation> oberdorferTableReservations = new ArrayList<>();
        Reservation reservation = new Reservation("1","1","4","sterner","4.4.2020 14:20", "4.4.2020 15:50");
        oberdorferTableReservations.add(reservation);
        Table oberndorferTable = new Table("1","1","4", oberdorferTableReservations);
        oberndorferTables.add(oberndorferTable);
        restaurantRepository.save(new Restaurant("1", "13.9559", "48.2054", "Oberndorfer", oberndorferTables));
        restaurantRepository.save(new Restaurant("2", "88", "50", "Hofwirt", new ArrayList<Table>()));
    }
}
