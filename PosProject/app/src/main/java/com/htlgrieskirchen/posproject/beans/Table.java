package com.htlgrieskirchen.posproject.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Table implements Parcelable {

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

    protected Table(Parcel in) {
        tableNumber = in.readInt();
        reservations = in.readParcelableList(new ArrayList<>(), Reservation.class.getClassLoader());
        seats = in.readInt();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tableNumber);
        dest.writeParcelableList(reservations, flags);
        dest.writeInt(seats);
    }
}
