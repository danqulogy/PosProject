package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationSendActivity extends AppCompatActivity {

    Restaurant restaurant;
    Button dateButton;
    EditText date;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_send);
        this.context = this;
        restaurant = getIntent().getParcelableExtra("restaurant");

        dateButton = findViewById(R.id.reservation_send_date_button);
        date = findViewById(R.id.reservation_send_date);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DatePickerDialog datePickerDialog = new DatePickerDialog(context);
              datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(LocalDate.of(year,month+1,dayOfMonth).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    }
                });
              datePickerDialog.show();
            }
        });


    }
}