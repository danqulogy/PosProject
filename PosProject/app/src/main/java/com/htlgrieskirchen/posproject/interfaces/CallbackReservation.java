package com.htlgrieskirchen.posproject.interfaces;

import com.htlgrieskirchen.posproject.beans.Reservation;

public interface CallbackReservation {

    void onSuccess(String method, Reservation reservation);
    void onFailure(String response);

}
