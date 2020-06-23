package at.htlgrieskirchen.sternerwimmer.api;

import at.htlgrieskirchen.sternerwimmer.api.classes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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

    @GetMapping("/getReservationById")
    public Reservation getReservationById(@RequestParam(value = "id") String id) {
        List<Reservation> reservations = new ArrayList<>();
        restaurantRepository.findAll().stream().forEach(r -> r.getTables().forEach(t -> reservations.addAll(t.getReservations())));
        return reservations.stream().filter(r -> r.getId().equals(id)).findFirst().get();
    }

    @DeleteMapping("/deleteReservation")
    public List<Restaurant> deleteReserevationById(@RequestParam(value = "id") String id) {
        Reservation reservation = getReservationById(id);
        Restaurant restaurant = restaurantRepository.findByRestaurantNumber(reservation.getRestaurantNumber());
        Table table = restaurant.getTables().get(Integer.parseInt(reservation.getTableNumber()) - 1);
        restaurant.getTables().get(Integer.parseInt(reservation.getTableNumber()) - 1).getReservations().remove(reservation);
        restaurantRepository.save(restaurant);
        return restaurantRepository.findAll();
    }

    @PutMapping("/addReservation")
    public ReservationDto addReservation(@RequestBody ReservationDto reservationDto) {
        Restaurant restaurant = restaurantRepository.findByRestaurantNumber(reservationDto.getRestaurantNumber());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime start = LocalDateTime.parse(reservationDto.getReservationStart(), dtf);
        LocalDateTime end = LocalDateTime.parse(reservationDto.getReservationEnd(), dtf);
        List<Reservation> reservations = new ArrayList<>();
        List<String> tables = new ArrayList<>();
        restaurant.getTables().stream()
                .sorted((Comparator.comparingInt(t -> Integer.parseInt(t.getChairsAvailable()))))
                .filter(t -> Integer.parseInt(t.getChairsAvailable()) >= Integer.parseInt(reservationDto.getChairs())).forEach(t -> reservations.addAll(t.getReservations()));
        for (Reservation r :
                reservations) {
            //a.start < b.end && b.start < a.end
            LocalDateTime startR = LocalDateTime.parse(r.getReservationStart(), dtf);
            LocalDateTime endR = LocalDateTime.parse(r.getReservationEnd(), dtf);
            boolean overlap = start.isBefore(endR) && startR.isBefore(end);
            if (overlap) {
                if (tables.contains(r.getTableNumber())) {
                    tables.remove(r.getTableNumber());
                }
            } else if (!overlap) {
                if (tables.contains(r.getTableNumber())) {

                } else {
                    tables.add(r.getTableNumber());
                }
            }
            int tableIndex = Integer.parseInt(tables.stream().findFirst().orElse("-1")) - 1;
            if (tableIndex > -1) {
                restaurant.getTables().get(tableIndex).getReservations().add(new Reservation(reservationDto.getRestaurantNumber(), String.valueOf(tableIndex + 1), reservationDto.getId(), reservationDto.getName(), reservationDto.getChairs(), reservationDto.getReservationStart(), reservationDto.getReservationEnd()));
                reservationDto.setTableNumber(String.valueOf(tableIndex + 1));
                restaurantRepository.save(restaurant);
                return reservationDto;
            } else return null;
        }
        int tableIndex = Integer.parseInt(restaurant.getTables().stream().sorted((Comparator.comparingInt(t -> Integer.parseInt(t.getChairsAvailable())))).filter(t -> Integer.parseInt(t.getChairsAvailable()) >= Integer.parseInt(reservationDto.getChairs())
        ).findFirst().orElse(null).getId()) - 1;
        restaurant.getTables().get(tableIndex).getReservations().add(new Reservation(reservationDto.getRestaurantNumber(), String.valueOf(tableIndex + 1), reservationDto.getId(), reservationDto.getName(), reservationDto.getChairs(), reservationDto.getReservationStart(), reservationDto.getReservationEnd()));
        reservationDto.setTableNumber(String.valueOf(tableIndex + 1));
        restaurantRepository.save(restaurant);
        return reservationDto;
    }
}

