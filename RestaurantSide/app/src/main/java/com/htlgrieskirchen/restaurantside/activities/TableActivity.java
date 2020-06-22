package com.htlgrieskirchen.restaurantside.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.restaurantside.Config;
import com.htlgrieskirchen.restaurantside.R;
import com.htlgrieskirchen.restaurantside.adapters.ReservationAdapter;
import com.htlgrieskirchen.restaurantside.beans.Reservation;
import com.htlgrieskirchen.restaurantside.beans.Restaurant;
import com.htlgrieskirchen.restaurantside.beans.Table;
import com.htlgrieskirchen.restaurantside.handlers.RestaurantHandler;
import com.htlgrieskirchen.restaurantside.interfaces.CallbackReservation;
import com.htlgrieskirchen.restaurantside.tasks.ReservationTask;
import com.htlgrieskirchen.restaurantside.tasks.RestaurantTask;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity implements CallbackReservation {

    private Table table;
    private CallbackReservation callback = this;
    private ReservationAdapter adapter;
    private ListView listView;
    private Reservation puttedReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        table = getIntent().getParcelableExtra("table");

        ((TextView) findViewById(R.id.activity_table_name)).setText("Table " + table.getId());
        listView = findViewById(R.id.activity_table_lv);
        adapter = new ReservationAdapter(this, R.layout.reservation_lv_item, table.getReservations());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            this.table = RestaurantHandler.getRestaurant().getTables().get(table.getId() - 1);

            Intent intent = new Intent(TableActivity.this, ReservationActivity.class);
            intent.putExtra("reservation", table.getReservations().get(position));
            startActivityForResult(intent, Config.RQ_RESERVATION_INTENT);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RQ_RESERVATION_INTENT && resultCode == Activity.RESULT_OK) {
            Reservation reservation = data.getParcelableExtra("reservation");
            RestaurantHandler.deleteReservation(reservation);
            table = RestaurantHandler.getTableById(table.getId());

            if (table == null){
                table = new Table();
                table.setReservations(new ArrayList<>());
            }

            adapter = new ReservationAdapter(this, R.layout.reservation_lv_item, table.getReservations());
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        int itemId = item.getItemId();

        if (itemId == R.id.table_manu_set_reserved) {
            Restaurant restaurant = RestaurantHandler.getRestaurant();
            LocalDateTime now = LocalDateTime.parse(dtf.format(LocalDateTime.now()), dtf);
            LocalDateTime end = LocalDateTime.parse(now.plusDays(1).toLocalDate().format(date) + " 06:00", dtf);
            Reservation reservation = new Reservation(restaurant.getRestaurantNumber(), table.getId(), randomReservationId(), "INTERN", table.getChairsAvailable(), now, end);

            puttedReservation = reservation;

            ReservationTask reservationTask = new ReservationTask(callback);

            JsonSerializer<LocalDateTime> localDateTimeJsonSerializer = new JsonSerializer<LocalDateTime>() {
                @Override
                public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(dtf));
                }
            };
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonSerializer);
            String string = gsonBuilder.create().toJson(reservation, new TypeToken<Reservation>() {
            }.getType());

            reservationTask.execute("PUT", string);
        }

        return super.onOptionsItemSelected(item);
    }

    private String randomReservationId() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randomIndex = secureRandom.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    @Override
    public void onSuccess(String method, Reservation reservation) {
        if (method.equals("PUT") && reservation != null) {
            Toast.makeText(this, "Table set as reserved", Toast.LENGTH_LONG).show();

            RestaurantHandler.addReservation(puttedReservation);
            adapter = new ReservationAdapter(this, R.layout.reservation_lv_item, RestaurantHandler.getRestaurant().getTables().get(puttedReservation.getTableNumber() - 1).getReservations());
            listView.setAdapter(adapter);

        } else {
            Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_LONG).show();
    }
}