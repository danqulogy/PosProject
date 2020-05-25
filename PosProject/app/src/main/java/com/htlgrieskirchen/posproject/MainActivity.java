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

import com.htlgrieskirchen.posproject.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

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
}
