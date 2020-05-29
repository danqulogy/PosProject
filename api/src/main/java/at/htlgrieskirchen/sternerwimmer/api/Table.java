package at.htlgrieskirchen.sternerwimmer.api;

import java.util.List;

public class Table {
    public String restaurantNumber;
    public String id;
    public String chairsAvailable;
    public List<Reservation> reservations;


    public Table() {
    }

    public Table(String restaurantNumber,String id,String chairsAvailable, List<Reservation> reservations) {
        this.restaurantNumber = restaurantNumber;
        this.id = id;
        this.chairsAvailable = chairsAvailable;
        this.reservations = reservations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }


    public String getChairsAvailable() {
        return chairsAvailable;
    }

    public void setChairsAvailable(String chairsAvailable) {
        this.chairsAvailable = chairsAvailable;
    }


}
