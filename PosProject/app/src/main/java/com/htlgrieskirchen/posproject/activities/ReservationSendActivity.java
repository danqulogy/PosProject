package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.beans.Table;
import com.htlgrieskirchen.posproject.handlers.ReservationHandler;
import com.htlgrieskirchen.posproject.interfaces.CallbackReservation;
import com.htlgrieskirchen.posproject.tasks.ReservationTask;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReservationSendActivity extends AppCompatActivity implements CallbackReservation {

    Restaurant restaurant;
    Button dateButton;
    TextView date;
    TextView openingTimes;
    Context context;
    Button reserveButton;
    CallbackReservation callback = this;
    Spinner spinner;
    TextView persons;
    TextView restaurantInfo;
    EditText name;
    EditText start;
    EditText end;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_send);
        this.context = this;
        restaurantInfo = findViewById(R.id.reservation_send_restaurantinformation);
        restaurant = getIntent().getParcelableExtra("restaurant");
        String sRestaurantInfo = getResources().getString(R.string.restaurant);
        sRestaurantInfo += " ";
        sRestaurantInfo += restaurant.getName();
        restaurantInfo.setText(sRestaurantInfo);
        reserveButton = findViewById(R.id.reservation_send_button_reserve);


        name = findViewById(R.id.reservation_send_name);
        start = findViewById(R.id.reservation_send_start);
        end = findViewById(R.id.reservation_send_end);

        persons = findViewById(R.id.reservation_send_persons);
        persons.setText("0");

        openingTimes = findViewById(R.id.reservation_send_opening_time);
        openingTimes.setText(restaurant.getOpeningTimes());

        dateButton = findViewById(R.id.reservation_send_date_button);
        date = findViewById(R.id.reservation_send_date);
        date.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(LocalDate.of(year, month + 1, dayOfMonth).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    }
                });
                datePickerDialog.show();
            }
        });

        spinner = findViewById(R.id.reservation_send_persons_spinner);
        ArrayList<Integer> spinnerValues = new ArrayList<>();
        for (int i = 0; i < restaurant.getTables().stream().mapToInt(Table::getChairsAvailable).max().getAsInt(); i++) {
            spinnerValues.add(i + 1);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerValues);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                persons.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start.getText().toString().isEmpty()){
                    makeToast("Start-Time has to be clarified");
                }else if(end.getText().toString().isEmpty()){
                    makeToast("End-Time has to be clarified");
                }else if(name.getText().toString().isEmpty()){
                    makeToast("Please enter a name for the reservation");
                }else{
                    ReservationTask reservationTask = new ReservationTask(callback);
                    LocalDateTime startDate = LocalDateTime.parse(date.getText().toString()+" "+start.getText().toString(), dtf);
                    LocalDateTime endDate = LocalDateTime.parse(date.getText().toString()+" "+end.getText().toString(), dtf);
                    Reservation reservation = new Reservation(restaurant.getRestaurantNumber()
                            , restaurant.getTables().stream()
                            .filter(t -> t.getChairsAvailable() >= Integer.parseInt(persons.getText().toString()))
                            .findFirst().get().getId()
                            , randomReservationId()
                            , name.getText().toString()
                            , Integer.parseInt(persons.getText().toString())
                            , startDate
                            , endDate);

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                        @Override
                        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                            return new JsonPrimitive(src.format(dtf));
                        }
                    });
                    Gson gson = gsonBuilder.create();
                    reservationTask.execute("PUT", gson.toJson(reservation, Reservation.class));
                }
            }
        });
    }

    private String randomReservationId(){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < 8; i++){
            int randomIndex = secureRandom.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    private void makeToast(String response){
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(String method, Reservation reservation) {
        if(method.equals("PUT") && reservation != null){
            Intent data = new Intent();
            data.putExtra("response", "Reservation set");
            data.putExtra("reservation", reservation);
            setResult(Config.RQ_RESERVATION_INTENT, data);
            finish();
        }else{
            onFailure("Something went wrong, please try again");
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }
}