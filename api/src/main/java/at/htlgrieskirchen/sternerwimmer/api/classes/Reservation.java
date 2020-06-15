package at.htlgrieskirchen.sternerwimmer.api.classes;

import java.util.Objects;

public class Reservation {

    public String restaurantNumber;
    public String tableNumber;
    public String id;
    public String chairs;
    public String reservationStart;
    public String reservationEnd;

    public Reservation() {
    }

    public Reservation(String restaurantNumber, String tableNumber, String id, String chairs, String reservationStart, String reservationEnd) {
        this.restaurantNumber = restaurantNumber;
        this.tableNumber = tableNumber;
        this.chairs = chairs;
        this.id = id;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    public String getRestaurantNumber() {
        return restaurantNumber;
    }

    public String getChairs() {
        return chairs;
    }

    public void setChairs(String chairs) {
        this.chairs = chairs;
    }

    public void setRestaurantNumber(String restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(String reservationStart) {
        this.reservationStart = reservationStart;
    }

    public String getReservationEnd() {
        return reservationEnd;
    }

    public String getId() {
        return id;
    }

    public void setReservationEnd(String reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "restaurantNumber='" + restaurantNumber + '\'' +
                ", tableNumber='" + tableNumber + '\'' +
                ", id='" + id + '\'' +
                ", chairs='" + chairs + '\'' +
                ", reservationStart='" + reservationStart + '\'' +
                ", reservationEnd='" + reservationEnd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(restaurantNumber, that.restaurantNumber) &&
                Objects.equals(tableNumber, that.tableNumber) &&
                Objects.equals(id, that.id) &&
                Objects.equals(chairs, that.chairs) &&
                Objects.equals(reservationStart, that.reservationStart) &&
                Objects.equals(reservationEnd, that.reservationEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantNumber, tableNumber, id, chairs, reservationStart, reservationEnd);
    }
}
