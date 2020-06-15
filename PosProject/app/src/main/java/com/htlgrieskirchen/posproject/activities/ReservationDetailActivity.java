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

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationDetailActivity extends AppCompatActivity {

    Reservation reservation;

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

            TextView tv = findViewById(R.id.detail_fragment_name);
            //tv.setText(restaurantName);
            tv = findViewById(R.id.reservation_detail_date);
            tv.setText(date.format(dateFormatter));
            tv = findViewById(R.id.reservation_detail_time_start);
            tv.setText(timeFormatter.format(startTime));
            tv = findViewById(R.id.reservation_detail_time_end);
            tv.setText(timeFormatter.format(endTime));

            Button button = findViewById(R.id.reservation_detail_cancel);
            button.setOnClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReservationDetailActivity.this);
                alert.setMessage("Do you really want to cancel the reservation?");
                alert.setPositiveButton("Yes", (dialog, which) -> {
                    //Cancel the reservation
                }).setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();
            });
        }
    }
}