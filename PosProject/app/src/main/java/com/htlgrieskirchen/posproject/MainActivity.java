package com.htlgrieskirchen.posproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.activities.DetailActivity;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.fragments.DetailFragment;
import com.htlgrieskirchen.posproject.fragments.MainFragment;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;
import com.htlgrieskirchen.posproject.interfaces.OnSelectionChangedListener;
import com.htlgrieskirchen.posproject.settings.SettingsActivity;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSelectionChangedListener, CallbackRestaurant {

    private CallbackRestaurant callback = this;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private LocationManager locationManager;
    private DetailFragment detailFragment;
    private MainFragment mainFragment;
    private boolean showDetail = false;
    private boolean locationPerm = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceChangeListener = this::setPreferenceChangeListener;
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFrag);
        showDetail = detailFragment != null && detailFragment.isInLayout();

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFrag);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        if (requestCode == Config.RQ_FINE_LOCATION) {
            if (grantResult.length > 0 && grantResult[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Location Permission denied");
                Toast.makeText(this, "Location Permission got denied", Toast.LENGTH_LONG).show();
                locationPerm = false;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if(itemId == R.id.main_menu_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, Config.RC_SETTINGS);
        }else if(itemId == R.id.main_menu_search){
            RestaurantTask restaurantTask = new RestaurantTask(callback);
            final View view = getLayoutInflater().inflate(R.layout.dialog_search, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setView(view);

            alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText name  = view.findViewById(R.id.dialog_search_et);
                    if(name.getText() != null){
                        String restaurantName = name.getText().toString();
                        if(!restaurantName.isEmpty()){
                            restaurantTask.execute("SEARCH", restaurantName);
                        }else{
                            Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.show();
        }else if(itemId == R.id.main_menu_nearest){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.RQ_FINE_LOCATION);
            }

            if(locationPerm){
                RestaurantTask task = new RestaurantTask(callback);
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                String provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(provider);
                if(location != null){
                    String lon = String.valueOf(location.getLongitude());
                    String lat = String.valueOf(location.getLatitude());
                    task.execute("NEAREST", lon, lat);
                }
            }else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final View view = getLayoutInflater().inflate(R.layout.dialog_address, null);
                alert.setView(view);

                Button b1 = view.findViewById(R.id.dialog_address_btn_switch_lon_lat);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.findViewById(R.id.dialog_address_address).setVisibility(View.GONE);
                        view.findViewById(R.id.dialog_address_lon_lat).setVisibility(View.VISIBLE);
                    }
                });

                Button b2 = view.findViewById(R.id.dialog_address_btn_switch_address);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.findViewById(R.id.dialog_address_address).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.dialog_address_lon_lat).setVisibility(View.GONE);
                    }
                });

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_street = view.findViewById(R.id.dialog_address_et_street);
                        EditText et_town = view.findViewById(R.id.dialog_address_et_town);
                        EditText et_lon = view.findViewById(R.id.dialog_address_et_lon);
                        EditText et_lat = view.findViewById(R.id.dialog_address_et_lat);

                        if(view.findViewById(R.id.dialog_address_address).getVisibility() == View.VISIBLE){
                            String street = et_street.getText().toString();
                            String town = et_town.getText().toString();
                            if(street.isEmpty()||town.isEmpty()){
                                Toast.makeText(MainActivity.this, "Please enter name of street and town", Toast.LENGTH_LONG).show();
                            }else{
                                //LocationIQ on server or client
                            }
                        }else{
                            String lon = et_lon.getText().toString();
                            String lat = et_lat.getText().toString();
                            if(lon.isEmpty() || lat.isEmpty()){
                                Toast.makeText(MainActivity.this, "Please enter longitude and latitude", Toast.LENGTH_LONG).show();
                            }else{
                                //RestaurantTask for lon and lat nearest
                            }
                        }
                    }
                }).setNegativeButton("Cancer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
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

    @Override
    public void onSuccess(List<Restaurant> restaurants) {
        if(restaurants == null) Toast.makeText(this, "There is no Restaurant with this name registered in our system", Toast.LENGTH_LONG).show();
        mainFragment.updateLV(restaurants);
    }

    @Override
    public void onFailure(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}
