package com.htlgrieskirchen.posproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int orientation = getResources().getConfiguration().orientation;
        if(orientation != Configuration.ORIENTATION_PORTRAIT){
            finish();
            return;
        }

    }

    private void handleIntent(){
        Intent intent = getIntent();
        if(intent == null){
            try{
                DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFrag);
                Restaurant restaurant =  intent.getParcelableExtra("restaurant");
                detailFragment.showInformation(restaurant);
            }catch (Exception e){
                Toast.makeText(DetailActivity.this, "An unexpected error occurred", Toast.LENGTH_LONG).show();
            }
        }
    }
}