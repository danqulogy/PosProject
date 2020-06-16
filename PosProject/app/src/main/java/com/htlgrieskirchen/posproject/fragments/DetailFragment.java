package com.htlgrieskirchen.posproject.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private TextView tvName;
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

            //result should be pushed to server (Reservation id should be saved on de device)
            //ReservationId is specific and the id reservations(and the id) should be shown in my Reservations
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Config.RQ_RESERVATION_INTENT && resultCode == Activity.RESULT_OK){
            Restaurant outputRestaurant = data.getParcelableExtra("restaurant");
            if(restaurant != outputRestaurant){
                RestaurantTask restaurantTask = new RestaurantTask();
                Gson gson = new Gson();
                String restaurantJson = gson.toJson(outputRestaurant);
                restaurantTask.execute("PUT", restaurantJson);
            }
        }
    }

    private void initializeView(View view){
        tvName = view.findViewById(R.id.detail_fragment_name);
    }

    public void showInformation(Restaurant restaurant){
        this.restaurant = restaurant;
        tvName.setText(restaurant.getName());
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


    private void initGoogleMapView(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(Config.MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
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