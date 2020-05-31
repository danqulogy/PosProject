package com.htlgrieskirchen.posproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.activities.DetailActivity;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.fragments.DetailFragment;
import com.htlgrieskirchen.posproject.interfaces.OnSelectionChangedListener;
import com.htlgrieskirchen.posproject.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements OnSelectionChangedListener {

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private DetailFragment detailFragment;
    private boolean showDetail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceChangeListener = this::setPreferenceChangeListener;
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.RQ_FINE_LOCATION);
        }

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFrag);
        showDetail = detailFragment != null && detailFragment.isInLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        if (requestCode == Config.RQ_FINE_LOCATION) {
            if (grantResult.length > 0 && grantResult[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Location Permission denied");
                Toast.makeText(this, "Location Permission got denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if(itemId == R.id.main_menu_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, Config.RC_SETTINGS);
        }

        return super.onOptionsItemSelected(item);
    }





    private void setPreferenceChangeListener(SharedPreferences sharedPreferences, String key){
        if(key.equals("themes")){
            int theme = Integer.parseInt(sharedPreferences.getString(key, 0+""));
        }
    }

    @Override
    public void onSelectionChanged(Restaurant restaurant) {
        if(showDetail) detailFragment.showInformation(restaurant);
        else callActivity(restaurant);
    }

    private void callActivity(Restaurant restaurant){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }
}
