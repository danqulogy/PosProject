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
        Reservation reservation = new Reservation("1","1","sterner", "Sterner","4","04.04.2020 14:20", "04.04.2020 15:50");
        oberdorferTableReservations.add(reservation);
        Table oberndorferTable1 = new Table("1","1","4", oberdorferTableReservations);
        oberndorferTables.add(oberndorferTable1);
        List<Reservation> oberdorferTableReservations2 = new ArrayList<>();
        Reservation reservation2 = new Reservation("1","2","wimmer", "Wimmer","8","04.04.2020 14:20", "04.04.2020 15:50");
        oberdorferTableReservations2.add(reservation2);
        Table oberndorferTable2 = new Table("1","2","8", oberdorferTableReservations2);
        restaurantRepository.save(new Restaurant("1", "13.9559", "48.2054", "Oberndorfer","Montag: 10:00 - 22:00\nDienstag 10:00 - 22:00\nMittwoch: Ruhetag\nDonnerstag: 10:00 - 22:00\nFreitag: 11:00 - 00:00\nSamstag: 11:00 - 00:00\nSonntag: 10:00 - 19:00","infos", oberndorferTables));
        restaurantRepository.save(new Restaurant("2", "88", "50", "Hofwirt","Montag: Ruhetag\nDienstag 10:00 - 22:00\nMittwoch: 10:00 - 22:00\nDonnerstag: \nFreitag: 11:00 - 00:00\nSamstag: 11:00 - 00:00\nSonntag: 10:00 - 19:00","infos", new ArrayList<Table>()));
    }
}
