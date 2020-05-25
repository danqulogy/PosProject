package com.htlgrieskirchen.posproject.callbacks;

import com.htlgrieskirchen.posproject.beans.Restaurant;

import java.util.List;

public interface CallbackRestaurant {

    void onSuccess(List<Restaurant> restaurants);
    void onFailure();

}
