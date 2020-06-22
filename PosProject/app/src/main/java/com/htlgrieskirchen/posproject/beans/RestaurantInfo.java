package com.htlgrieskirchen.posproject.beans;

public class RestaurantInfo {

    private int restaurantNumber;
    private String name;
    private Restaurant restaurant;

    public RestaurantInfo(int restaurantNumber, String name) {
        this.restaurantNumber = restaurantNumber;
        this.name = name;
    }

    public RestaurantInfo(int restaurantNumber, String name, Restaurant restaurant) {
        this.restaurantNumber = restaurantNumber;
        this.name = name;
        this.restaurant = restaurant;
    }

    public int getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(int restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
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
