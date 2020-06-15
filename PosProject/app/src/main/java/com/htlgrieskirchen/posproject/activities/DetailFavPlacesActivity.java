package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.handlers.RestaurantInfoHandler;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.util.List;

public class DetailFavPlacesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView tv = findViewById(R.id.detail_fragment_name);
        tv.setText(name);
    }
}