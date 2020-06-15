package com.htlgrieskirchen.posproject.beans;

public class RestaurantInfo {

    private String dbId;
    private String name;
    private Restaurant restaurant;

    public RestaurantInfo(String dbId, String name) {
        this.dbId = dbId;
        this.name = name;
    }

    public RestaurantInfo(String dbId, String name, Restaurant restaurant) {
        this.dbId = dbId;
        this.name = name;
        this.restaurant = restaurant;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
