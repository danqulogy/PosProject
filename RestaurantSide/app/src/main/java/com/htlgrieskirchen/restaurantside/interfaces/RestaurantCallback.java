package com.htlgrieskirchen.restaurantside.interfaces;

import com.htlgrieskirchen.restaurantside.beans.Restaurant;

public interface RestaurantCallback {

    void onSuccess(String method, Restaurant restaurant);
    void onFailure(String response);
}
