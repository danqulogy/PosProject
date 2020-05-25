package com.htlgrieskirchen.posproject.beans;

import java.util.List;

public class Restaurant {

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
}
