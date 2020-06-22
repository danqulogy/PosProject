package com.htlgrieskirchen.posproject.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.gson.Gson;
import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.activities.DetailActivity;
import com.htlgrieskirchen.posproject.activities.ReservationSendActivity;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.handlers.ReservationHandler;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.io.FileNotFoundException;

public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private TextView tvName;
    private TextView tvInfo;
    private Button reserve;
    private Restaurant restaurant;
    MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initializeView(view);
        mMapView = view.findViewById(R.id.detail_fragment_mapview);
        initGoogleMapView(savedInstanceState);
        reserve = view.findViewById(R.id.fragment_detail_button);
        reserve.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReservationSendActivity.class);
            intent.putExtra("restaurant", restaurant);
            startActivityForResult(intent, Config.RQ_RESERVATION_INTENT);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            String response = data.getStringExtra("response");
            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

            Reservation reservation = data.getParcelableExtra("reservation");
            if (reservation != null) {
                ReservationHandler.addReservation(reservation);
                try {
                    ReservationHandler.saveReservations(getContext().openFileOutput(Config.FILE_RESERVATIONS, Context.MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void initializeView(View view) {
        tvName = view.findViewById(R.id.detail_fragment_name);
        tvInfo = view.findViewById(R.id.detail_fragment_info);
    }

    public void showInformation(Restaurant restaurant) {
        this.restaurant = restaurant;
        tvName.setText(restaurant.getName());
        tvInfo.setText(restaurant.getInfos());
    }


    //MapView Behaviour and functions
    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(restaurant.getLat(), restaurant.getLon())).title(restaurant.getName()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(restaurant.getLat(), restaurant.getLon()), 15.0f));
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


    private void initGoogleMapView(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Config.MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(Config.MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
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