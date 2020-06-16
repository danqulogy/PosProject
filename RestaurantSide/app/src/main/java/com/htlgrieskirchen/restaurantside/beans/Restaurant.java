package com.htlgrieskirchen.restaurantside.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Parcelable{

    private String id;
    private int restaurantNumber;
    private double lon;
    private double lat;
    private String name;
    private List<Table> tables;

    public Restaurant() {
    }

    public Restaurant(String id, int restaurantNumber, double lon, double lat, String name, List<Table> tables) {
        this.id = id;
        this.restaurantNumber = restaurantNumber;
        this.lon = lon;
        this.lat = lat;
        this.name = name;
        this.tables = tables;
    }

    protected Restaurant(Parcel in) {
        id = in.readString();
        restaurantNumber = in.readInt();
        lon = in.readDouble();
        lat = in.readDouble();
        name = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(int restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(id);
        dest.writeInt(restaurantNumber);
        dest.writeDouble(lon);
        dest.writeDouble(lat);
        dest.writeString(name);
        dest.writeParcelableList(tables, flags);
    }
}
