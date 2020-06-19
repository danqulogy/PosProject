package com.htlgrieskirchen.restaurantside.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.htlgrieskirchen.restaurantside.R;
import com.htlgrieskirchen.restaurantside.beans.Reservation;
import com.htlgrieskirchen.restaurantside.beans.Restaurant;
import com.htlgrieskirchen.restaurantside.handlers.RestaurantHandler;
import com.htlgrieskirchen.restaurantside.interfaces.CallbackReservation;
import com.htlgrieskirchen.restaurantside.tasks.ReservationTask;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;

public class ReservationActivity extends AppCompatActivity implements CallbackReservation {

    CallbackReservation callback = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");

        Reservation reservation = getIntent().getParcelableExtra("reservation");

        ((TextView) findViewById(R.id.reservation_detail_table_name)).setText("Table "+reservation.getTableNumber());
        ((TextView) findViewById(R.id.reservation_detail_id)).setText(reservation.getId());
        ((TextView) findViewById(R.id.reservation_detail_date)).setText(reservation.getReservationStart().toLocalDate().format(date));
        ((TextView) findViewById(R.id.reservation_detail_time_start)).setText(reservation.getReservationStart().toLocalTime().format(time));
        ((TextView) findViewById(R.id.reservation_detail_time_end)).setText(reservation.getReservationEnd().toLocalTime().format(time));
        ((TextView) findViewById(R.id.reservation_detail_persons_number)).setText(String.valueOf(reservation.getChairs()));
        ((TextView) findViewById(R.id.reservation_detail_name)).setText(reservation.getName());

        Button button = findViewById(R.id.reservation_detail_cancel);
        button.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(ReservationActivity.this);
            alert.setTitle("Cancel Reservation "+reservation.getId());
            alert.setMessage("Are you sure you want to cancel the reservation?");
            alert.setPositiveButton("Yes", (dialog, which) -> {
                RestaurantHandler.deleteReservation(reservation);
                ReservationTask task = new ReservationTask(callback);
                task.execute("DELETE", reservation.getId());
            }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();
        });
    }


    @Override
    public void onSuccess(String method, Reservation reservation) {
        if(method.equals("DELETE") && reservation != null){
            Toast.makeText(ReservationActivity.this, "Reservation canceled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(ReservationActivity.this, response, Toast.LENGTH_LONG).show();
    }
}