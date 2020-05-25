package com.htlgrieskirchen.posproject.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reservation implements Parcelable {

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

    protected Reservation(Parcel in) {
        reservationNumber = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reservationNumber);
        dest.writeSerializable(reservationStart);
        dest.writeSerializable(reservationEnd);
    }
}
