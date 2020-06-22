package com.htlgrieskirchen.restaurantside.handlers;

import com.htlgrieskirchen.restaurantside.beans.Reservation;
import com.htlgrieskirchen.restaurantside.beans.Restaurant;
import com.htlgrieskirchen.restaurantside.beans.Table;

import java.util.List;

public class RestaurantHandler {

    private static Restaurant restaurant;

    public static Restaurant getRestaurant() {
        return restaurant;
    }

    public static void setRestaurant(Restaurant restaurant) {
        RestaurantHandler.restaurant = restaurant;
    }

    public static void deleteReservation(Reservation reservation){
        for(int i = 0; i < restaurant.getTables().size(); i ++){
            if(restaurant.getTables().get(i).getId() == reservation.getTableNumber()) restaurant.getTables().get(i).getReservations().remove(reservation);
        }
    }

    public static void addReservation(Reservation reservation){
        for(Table t: restaurant.getTables()){
            if(t.getId() == reservation.getTableNumber()){
                t.getReservations().add(reservation);
            }
        }
    }

    public static Table getTableById(int id){
        for(Table t: restaurant.getTables()){
            if(t.getId() == id) return t;
        }
        return restaurant.getTables().get(id-1);
    }
}
