package com.htlgrieskirchen.posproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.beans.Table;
import com.htlgrieskirchen.posproject.interfaces.CallbackReservation;
import com.htlgrieskirchen.posproject.tasks.ReservationTask;

import java.security.SecureRandom;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public class ReservationSendActivity extends AppCompatActivity implements CallbackReservation {

    Restaurant restaurant;
    CalendarView calendarView;
    LocalDate localDate;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    CallbackReservation callback = this;


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

        EditText start = findViewById(R.id.reservation_send_start);
        EditText end = findViewById(R.id.reservation_send_end);
        String timeStart = start.getText().toString();
        String timeEnd = end.getText().toString();
        String name = ((EditText) findViewById(R.id.reservatin_send_name)).getText().toString();
        String sSeats = ((EditText)findViewById(R.id.reservation_send_seats)).getText().toString();


        Button button = findViewById(R.id.reservation_send_button_reserve);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeStart.isEmpty() || timeEnd.isEmpty() || !timeEnd.contains(":") || !timeStart.contains(":") || localDate == null || localDate.isBefore(LocalDate.now()) || name.isEmpty() || sSeats.isEmpty()){
                    if(timeStart.isEmpty()) makeToast("Time-start-field is not allowed to be empty");
                    if(timeEnd.isEmpty()) makeToast("Time-end-field is not allowed to be empty");
                    if(!timeEnd.contains(":")) makeToast("Time-end has wrong format");
                    if(!timeStart.contains(":")) makeToast("Time-start has wrong format");
                    if (name.isEmpty()) makeToast("Name-field is not allowed to be empty");
                    if(sSeats.isEmpty()) makeToast("Seats-field is not allowed to be empty");
                    if(localDate == null) makeToast("No date picked");
                    else if(localDate.isBefore(LocalDate.now())) makeToast("The picked date is in the past");
                }else{
                    String date = localDate.format(dateFormatter);
                    try{
                        LocalDateTime startDate = LocalDateTime.parse(date+" "+timeStart, dtf);
                        LocalDateTime endDate = LocalDateTime.parse(date+" "+timeEnd, dtf);

                        if(endDate.isBefore(startDate)){
                            endDate = endDate.plusDays(1);
                        }

                        int seats = Integer.parseInt(sSeats);

                        String id = randomReservationId();
                        int addSeats = 0;
                        int tableId = -1;
                        boolean b = false;


                        for(Table t: restaurant.getTables()){
                            if(t.getChairsAvailable() == seats+addSeats){
                                List<Reservation> reservations = t.getReservations();
                                for(int i = 0; i < reservations.size(); i ++){
                                    Reservation r = reservations.get(i);
                                    if(i == 0 && startDate.isBefore(r.getReservationStart()) && endDate.isBefore(r.getReservationEnd())){
                                        tableId = t.getId();
                                        b = true;
                                        break;
                                    }else if(i != 0){
                                        Reservation r2 = reservations.get(i-1);
                                        if(startDate.isAfter(r2.getReservationEnd()) && startDate.isBefore(r.getReservationStart()) && endDate.isBefore(r.getReservationEnd())){
                                            tableId = t.getId();
                                            b = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if(b) break;
                        }

                        if(tableId != -1){
                            Reservation reservation = new Reservation(restaurant.getRestaurantNumber(), tableId, id, name, seats, startDate, endDate);
                            ReservationTask task = new ReservationTask(callback);
                            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("d.M.yyyy HH:mm"))).create();
                            TypeToken<Reservation> typeToken = new TypeToken<Reservation>() {};
                            task.execute("PUT", gson.toJson(reservation, typeToken.getType()));
                        }else{
                            makeToast("No table with requested requirements available in this restaurant");
                        }

                    }catch (DateTimeParseException e){
                        makeToast("Wrong format with end or start time");
                    }
                }

            }
        });
    }

    private void makeToast(String massage){
        Toast.makeText(ReservationSendActivity.this , massage, Toast.LENGTH_LONG).show();
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

    @Override
    public void onSuccess(String method, Reservation reservation) {
        if(method.equals("PUT") && reservation!= null){
            makeToast("Reservation set");
            Intent intent = new Intent();
            intent.putExtra("response","Reservation has been saved an added to your Reservations");
            onActivityResult(Config.RQ_RESERVATION_INTENT, Activity.RESULT_OK, intent);
        }else{
            makeToast("An error occurred while setting your Reservation please try again");
        }
    }

    @Override
    public void onFailure(String response) {
        makeToast(response);
    }
}