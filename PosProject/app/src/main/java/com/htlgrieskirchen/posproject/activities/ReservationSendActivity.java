package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;

import java.time.Duration;
import java.time.LocalDate;

public class ReservationSendActivity extends AppCompatActivity {

    Restaurant restaurant;
    CalendarView calendarView;
    LocalDate localDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_send);
        calendarView = (CalendarView) findViewById(R.id.reservation_send_calendar);
        restaurant = getIntent().getParcelableExtra("restaurant");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //MONTH FROM 0-11
                localDate = LocalDate.of(year, month+1, dayOfMonth);
            }
        });
    }
}