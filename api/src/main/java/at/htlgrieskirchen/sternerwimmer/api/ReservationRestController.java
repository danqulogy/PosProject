package at.htlgrieskirchen.sternerwimmer.api;

import at.htlgrieskirchen.sternerwimmer.api.classes.Reservation;
import at.htlgrieskirchen.sternerwimmer.api.classes.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/reservations")
public class ReservationRestController {
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/getReservationsForTable")
    public List<Reservation> getReservationsForTable(@RequestParam(value = "restaurantNumber") String restaurantNumber, @RequestParam(value = "tableNumber") String tableNumber) {
        return restaurantRepository.findByRestaurantNumber(restaurantNumber).getTables().stream().filter(table -> table.getId().equals(tableNumber)).findFirst().get().getReservations();
    }

    @GetMapping("/getReservationById")
    public Reservation getReservationById(@RequestParam(value = "id") String id) {
        List<Reservation> reservations = new ArrayList<>();
        restaurantRepository.findAll().stream().forEach(r -> r.getTables().forEach(t -> reservations.addAll(t.getReservations())));
        return reservations.stream().filter(r -> r.getId().equals(id)).findFirst().get();
    }


}

