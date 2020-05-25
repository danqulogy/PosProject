package com.htlgrieskirchen.posproject.beans;

import java.util.List;

public class Table {

    private int tableNumber;
    private List<Reservation> reservations;
    private int seats;

    public Table() {
    }

    public Table(int tableNumber, List<Reservation> reservations, int seats) {
        this.tableNumber = tableNumber;
        this.reservations = reservations;
        this.seats = seats;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
