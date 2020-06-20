package com.htlgrieskirchen.restaurantside.interfaces;

import com.htlgrieskirchen.restaurantside.beans.Reservation;

public interface CallbackReservation {

    void onSuccess(String method, Reservation reservation);
    void onFailure(String response);
}
