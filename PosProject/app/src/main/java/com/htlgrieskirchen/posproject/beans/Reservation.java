package com.htlgrieskirchen.posproject.beans;

import java.time.LocalDateTime;

public class Reservation {

    private String reservationNumber;
    private LocalDateTime reservationStart;
    private LocalDateTime  reservationEnd;

    public Reservation() {
    }

    public Reservation(String reservationNumber, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.reservationNumber = reservationNumber;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }
}
