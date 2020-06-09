package com.htlgrieskirchen.posproject.interfaces;

import com.htlgrieskirchen.posproject.beans.Restaurant;

import java.util.List;

public interface CallbackRestaurant {

    void onSuccess(String method, List<Restaurant> restaurants);
    void onFailure(String errorMassage);

}
