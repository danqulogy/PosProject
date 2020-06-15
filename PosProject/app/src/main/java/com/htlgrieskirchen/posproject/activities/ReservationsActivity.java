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

import com.google.gson.internal.$Gson$Preconditions;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.adapters.ReservationsLVAdapter;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.handlers.ReservationHandler;

import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private List<Reservation> reservations;
    private ListView listView;
    ReservationsLVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

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
                //Task for Reservation get
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
}