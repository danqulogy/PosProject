package at.htlgrieskirchen.sternerwimmer.api.classes;

public class ReservationDto {
    private String restaurantNumber;
    private String tableNumber;
    private String id;
    private String chairs;
    private String reservationStart;
    private String reservationEnd;

    public ReservationDto(String restaurantNumber, String tableNumber, String id, String chairs, String reservationStart, String reservationEnd) {
        this.restaurantNumber = restaurantNumber;
        this.tableNumber = tableNumber;
        this.id = id;
        this.chairs = chairs;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    public ReservationDto() {
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "restaurantNumber='" + restaurantNumber + '\'' +
                ", tableNumber='" + tableNumber + '\'' +
                ", id='" + id + '\'' +
                ", chairs='" + chairs + '\'' +
                ", reservationStart='" + reservationStart + '\'' +
                ", reservationEnd='" + reservationEnd + '\'' +
                '}';
    }

    public String getRestaurantNumber() {
        return restaurantNumber;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChairs() {
        return chairs;
    }

    public void setChairs(String chairs) {
        this.chairs = chairs;
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

    public void setReservationEnd(String reservationEnd) {
        this.reservationEnd = reservationEnd;
    }
}
