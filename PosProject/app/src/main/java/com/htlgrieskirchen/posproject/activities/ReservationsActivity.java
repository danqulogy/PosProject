package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.internal.$Gson$Preconditions;
import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.adapters.ReservationsLVAdapter;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.handlers.ReservationHandler;
import com.htlgrieskirchen.posproject.interfaces.CallbackReservation;
import com.htlgrieskirchen.posproject.tasks.ReservationTask;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity implements CallbackReservation {

    private List<Reservation> reservations;
    private ListView listView;
    ReservationsLVAdapter adapter;
    CallbackReservation callback = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        try {
            ReservationHandler.readReservations(openFileInput(Config.FILE_RESERVATIONS));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        reservations = ReservationHandler.getReservationList();
        if(reservations == null){
            reservations = new ArrayList<>();
        }

        adapter = new ReservationsLVAdapter(this, R.layout.reservations_lv_item, reservations);
        listView = findViewById(R.id.reservations_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Reservation chosen = reservations.get(position);
            Intent intent = new Intent(ReservationsActivity.this, ReservationDetailActivity.class);
            intent.putExtra("reservation", chosen);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reservations_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.reservations_menu_add){
            final View view = getLayoutInflater().inflate(R.layout.dialog_add_reservation, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setView(view);
            alert.setTitle("Enter an existing ReservationID");
            alert.setPositiveButton("Submit", (dialog, which) -> {
                EditText et = view.findViewById(R.id.dialog_add_reservation_et);
                String reservationId = et.getText().toString();
                if(!reservationId.isEmpty() && reservationId.length() > 4){
                    ReservationTask task = new ReservationTask(callback);
                    task.execute("GET", reservationId);
                }
            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel()).show();

        }else if(itemId == R.id.reservation_menu_info){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Infos about your Reservations");
            alert.setMessage(R.string.reservationsInfo);
            alert.setNeutralButton("OK", null);
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(String method, Reservation reservation) {
        if(method.equals("GET")){
            this.reservations = ReservationHandler.getReservationList();
            boolean b = true;

            for(Reservation r: reservations){
                if (r.getId().equals(reservation.getId())) {
                    b = false;
                    break;
                }
            }

            if(b){
                ReservationHandler.addReservation(reservation);
                try {
                    ReservationHandler.safeReservations(openFileOutput(Config.FILE_RESERVATIONS, MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                this.reservations = ReservationHandler.getReservationList();
                adapter.notifyDataSetChanged();

            }else Toast.makeText(this, "Reservation already exists", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(this,response, Toast.LENGTH_LONG).show();
    }
}