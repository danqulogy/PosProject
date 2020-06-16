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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
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

public class ReservationDetailActivity extends AppCompatActivity implements CallbackReservation, CallbackRestaurant, OnMapReadyCallback {

    Reservation reservation;
    CallbackReservation callback = this;
    CallbackRestaurant callbackRestaurant = this;
    MapView mMapView;
    Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);

        reservation = getIntent().getParcelableExtra("reservation");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");

    initGoogleMapView(savedInstanceState);

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

    private void initGoogleMapView(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(Config.MAPVIEW_BUNDLE_KEY);
        }

        mMapView = (MapView) findViewById(R.id.reservation_detail_mapView);
        mMapView.onCreate(mapViewBundle);
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
           restaurant = restaurants.get(0);
           mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }

    //MapView Behaviour and functions
    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(restaurant.getLat(),restaurant.getLon())).title(restaurant.getName()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(restaurant.getLat(),restaurant.getLon()),15.0f));
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
      map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
          @Override
          public void onPoiClick(PointOfInterest pointOfInterest) {
            map.animateCamera(CameraUpdateFactory.newLatLng(pointOfInterest.latLng));
          }
      });
      map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
          @Override
          public void onMapLongClick(LatLng latLng) {
              map.addMarker(new MarkerOptions().position(latLng));
          }
      });
    }





    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(Config.MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(Config.MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}