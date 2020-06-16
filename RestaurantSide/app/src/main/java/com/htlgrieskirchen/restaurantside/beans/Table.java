package com.htlgrieskirchen.restaurantside.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Table implements Parcelable {

    private int restaurantNumber;
    private int id;
    private int chairsAvailable;
    private List<Reservation> reservations;

    public Table() {
    }

    public Table(int restaurantNumber, int id, int chairsAvailable, List<Reservation> reservations) {
        this.restaurantNumber = restaurantNumber;
        this.id = id;
        this.chairsAvailable = chairsAvailable;
        this.reservations = reservations;
    }

    protected Table(Parcel in) {
        restaurantNumber = in.readInt();
        id = in.readInt();
        chairsAvailable = in.readInt();
        reservations = in.readParcelableList(new ArrayList<>(), Reservation.class.getClassLoader());
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

    public int getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(int restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChairsAvailable() {
        return chairsAvailable;
    }

    public void setChairsAvailable(int chairsAvailable) {
        this.chairsAvailable = chairsAvailable;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(restaurantNumber);
        dest.writeInt(id);
        dest.writeInt(chairsAvailable);
        dest.writeParcelableList(reservations, flags);
    }
}
