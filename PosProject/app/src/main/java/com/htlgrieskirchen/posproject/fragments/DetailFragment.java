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

import com.google.gson.Gson;
import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.activities.DetailActivity;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

public class DetailFragment extends Fragment {

    private TextView tvName;
    private Button reserve;
    private Restaurant restaurant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initializeView(view);

        reserve = view.findViewById(R.id.fragment_detail_button);
        reserve.setOnClickListener(v -> {
            Intent intent = new Intent();
            //Intent with reservation screen ! !   !    !     !
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

    @Override
    public void onStart(){
        super.onStart();
    }


}