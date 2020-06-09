package at.htlgrieskirchen.sternerwimmer.api.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("/addReservation")
    //@RequestParam(value = "restaurantNumber")String restaurantNumber, @RequestParam(value = "tableNumber") String tableNumber,
    public void addReservation(@RequestBody Reservation reservation){
        List<Reservation> reservations = restaurantRepository.findByRestaurantNumber(reservation.getRestaurantNumber()).getTables().stream()
                .filter(table -> table.getId().equals(reservation.getTableNumber()))
                .findFirst().get().getReservations();
       reservations.add(reservation);
    }

}

