package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.handlers.ReservationHandler;
import com.htlgrieskirchen.posproject.interfaces.CallbackReservation;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;
import com.htlgrieskirchen.posproject.tasks.ReservationTask;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationDetailActivity extends AppCompatActivity implements CallbackReservation, CallbackRestaurant {

    Reservation reservation;
    CallbackReservation callback = this;
    CallbackRestaurant callbackRestaurant = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);

        reservation = getIntent().getParcelableExtra("reservation");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");


        if(reservation == null){
            Toast.makeText(this, "Error showing the chosen Reservation", Toast.LENGTH_LONG).show();
        }else{
            LocalDate date = reservation.getReservationStart().toLocalDate();
            LocalTime startTime = reservation.getReservationStart().toLocalTime();
            LocalTime endTime = reservation.getReservationEnd().toLocalTime();


            RestaurantTask restaurantTask = new RestaurantTask(callbackRestaurant);
            restaurantTask.execute("GETBYRESERVATION", reservation.getId());
            TextView tv = findViewById(R.id.reservation_detail_date);
            tv.setText(date.format(dateFormatter));
            tv = findViewById(R.id.reservation_detail_time_start);
            tv.setText(timeFormatter.format(startTime));
            tv = findViewById(R.id.reservation_detail_time_end);
            tv.setText(timeFormatter.format(endTime));
            tv = findViewById(R.id.reservation_detail_persons_number);
            tv.setText(String.valueOf(reservation.getChairs()));

            Button button = findViewById(R.id.reservation_detail_cancel);
            button.setOnClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReservationDetailActivity.this);
                alert.setMessage("Do you really want to cancel the reservation?");
                alert.setPositiveButton("Yes", (dialog, which) -> {
                    ReservationTask task = new ReservationTask(callback);
                    task.execute("DELETE", reservation.getId());
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();
            });
        }
    }

    @Override
    public void onSuccess(String method, Reservation reservation) {
        if(method.equals("DELETE")){
            if(reservation.getTableNumber() == -1){
                ReservationHandler.deleteReservation(reservation.getId());
                try {
                    ReservationHandler.safeReservations(openFileOutput(Config.FILE_RESERVATIONS, MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Your reservation has been canceled successfully", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(this, "Your reservation couldn't be canceled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(String method, List<Restaurant> restaurants) {
        if(method.equals("GETBYRESERVATION")){
            TextView tv = findViewById(R.id.reservation_detail_restaurant_name);
            tv.setText(restaurants.get(0).getName());
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }
}