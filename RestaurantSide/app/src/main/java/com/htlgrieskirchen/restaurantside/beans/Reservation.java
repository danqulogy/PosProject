package com.htlgrieskirchen.restaurantside.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation implements Parcelable{

    private int restaurantNumber;
    private int tableNumber;
    private String id;
    private String name;
    private int chairs;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;

    public Reservation() {
    }

    public Reservation(int restaurantNumber, int tableNumber, String id, String name, int chairs, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.restaurantNumber = restaurantNumber;
        this.tableNumber = tableNumber;
        this.id = id;
        this.name = name;
        this.chairs = chairs;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    protected Reservation(Parcel in) {
        restaurantNumber = in.readInt();
        tableNumber = in.readInt();
        id = in.readString();
        name = in.readString();
        chairs = in.readInt();
        reservationStart = (LocalDateTime) in.readSerializable();
        reservationEnd = (LocalDateTime) in.readSerializable();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    public int getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(int restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChairs() {
        return chairs;
    }

    public void setChairs(int chairs) {
        this.chairs = chairs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(restaurantNumber);
        dest.writeInt(tableNumber);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(chairs);
        dest.writeSerializable(reservationStart);
        dest.writeSerializable(reservationEnd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return restaurantNumber == that.restaurantNumber &&
                tableNumber == that.tableNumber &&
                chairs == that.chairs &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(reservationStart, that.reservationStart) &&
                Objects.equals(reservationEnd, that.reservationEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantNumber, tableNumber, id, name, chairs, reservationStart, reservationEnd);
    }
}
