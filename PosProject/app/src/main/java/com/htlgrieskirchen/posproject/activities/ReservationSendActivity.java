package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;

public class ReservationSendActivity extends AppCompatActivity {

    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_send);

        restaurant = getIntent().getParcelableExtra("restaurant");
    }
}