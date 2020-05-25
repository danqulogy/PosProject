package at.htlgrieskirchen.sternerwimmer.api;

import org.springframework.data.annotation.Id;

public class Reservation {
    @Id
    public String id;
    public String talbeNumber;
    public String reservationNumber;
    public String reservationStart;
    public String reservationEnd;

    public Reservation() {
    }

    public Reservation(String reservationNumber, String reservationStart, String reservationEnd) {
        this.reservationNumber = reservationNumber;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    public String getId() {
        return id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
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
