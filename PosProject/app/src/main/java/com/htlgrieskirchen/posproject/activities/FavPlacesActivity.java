package com.htlgrieskirchen.posproject.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.MainActivity;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.RestaurantInfoManager;
import com.htlgrieskirchen.posproject.adapters.FavPlacesLVAdapter;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.beans.RestaurantInfo;
import com.htlgrieskirchen.posproject.fragments.DetailFragment;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;
import com.htlgrieskirchen.posproject.settings.SettingsActivity;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FavPlacesActivity extends AppCompatActivity implements CallbackRestaurant {

    private ListView listView;
    private List<RestaurantInfo> infoList = new ArrayList<>();
    private FavPlacesLVAdapter adapter;
    private CallbackRestaurant callback = this;
    private List<Restaurant> restaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_places);

        listView = findViewById(R.id.fav_places_listview);
        try {
            infoList = RestaurantInfoManager.loadCurrentRestaurants(openFileInput(Config.FILE_FAV_PLACES));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(infoList != null && infoList.size() > 0 && restaurants.size() == 0){
            RestaurantTask task = new RestaurantTask(callback);
            task.execute("GETALL");
        }

        if(infoList == null) infoList = new ArrayList<>();
        adapter = new FavPlacesLVAdapter(this, R.layout.fav_places_lv_item, infoList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavPlacesActivity.this, DetailFavPlacesActivity.class);
                intent.putExtra("restaurant", infoList.get(position).getRestaurant());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_places_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if(itemId == R.id.fav_places_menu_add){
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
                            Toast.makeText(FavPlacesActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(String method, List<Restaurant> restaurants) {
        if(method.equals("DBID")){
            this.restaurants.addAll(restaurants);
            for (int i = 0; i < infoList.size(); i ++){
                if(method.contains(i+"")){
                    infoList.get(i).setRestaurant(restaurants.get(0));
                }
            }
        }else{
            if(restaurants != null && restaurants.size() != 0){
                for(RestaurantInfo r: infoList){
                    for(Restaurant rest: restaurants){
                        if(r.getDbId().equals(rest.getId())) r.setRestaurant(rest);
                    }
                }
                adapter.notifyDataSetChanged();
                try {
                    RestaurantInfoManager.safeCurrentRestaurants(openFileOutput(Config.FILE_FAV_PLACES, MODE_PRIVATE), infoList);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(FavPlacesActivity.this, "No Restaurant found with this name", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onFailure(String errorMassage) {
        Toast.makeText(FavPlacesActivity.this, "An error occurred while searching for the restaurant", Toast.LENGTH_LONG).show();
    }
}