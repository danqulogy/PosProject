package com.htlgrieskirchen.posproject.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Parcelable {

    private String name;
    private double lon;
    private double lat;
    private List<Table> tables;

    public Restaurant(String name, double lon, double lat, List<Table> tables) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.tables = tables;
    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        lon = in.readDouble();
        lat = in.readDouble();
        tables = in.readParcelableList(new ArrayList<>(), Table.class.getClassLoader());
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(lon);
        dest.writeDouble(lat);
        dest.writeParcelableList(tables, flags);
    }
}
