package com.htlgrieskirchen.posproject.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.handlers.RestaurantInfoHandler;
import com.htlgrieskirchen.posproject.adapters.FavPlacesLVAdapter;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.beans.RestaurantInfo;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.io.FileNotFoundException;
import java.util.List;

public class FavPlacesActivity extends AppCompatActivity implements CallbackRestaurant {

    private ListView listView;
    private FavPlacesLVAdapter adapter;
    private CallbackRestaurant callback = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_places);

        try {
            RestaurantInfoHandler.loadCurrentRestaurants(openFileInput(Config.FILE_FAV_PLACES));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.fav_places_listview);
        adapter = new FavPlacesLVAdapter(this, R.layout.fav_places_lv_item, RestaurantInfoHandler.getRestaurantInfos());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            RestaurantInfo info = RestaurantInfoHandler.getRestaurantInfos().get(position);

            RestaurantTask task = new RestaurantTask(callback);
            task.execute("GETBYNUMBER", String.valueOf(info.getRestaurantNumber()));


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

            alert.setPositiveButton("Search", (dialog, which) -> {
                EditText name  = view.findViewById(R.id.dialog_search_et);
                if(name.getText() != null){
                    String restaurantName = name.getText().toString();
                    if(!restaurantName.isEmpty()){
                        restaurantTask.execute("SEARCH", restaurantName);
                    }else{
                        Toast.makeText(FavPlacesActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
                    }
                }
            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(String method, List<Restaurant> restaurants) {
        if(method.equals("GETBYNUMBER")){
            RestaurantInfoHandler.addRestaurantToInfo(restaurants.get(0));

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("restaurant", restaurants.get(0));
            startActivity(intent);
        }else if(method.equals("SEARCH")){
            if(restaurants != null && restaurants.size() != 0){
                Restaurant restaurant = restaurants.get(0);
                boolean b = true;
                //For a non redundant list
                for(RestaurantInfo r: RestaurantInfoHandler.getRestaurantInfos()){
                    if (r.getRestaurantNumber() == restaurant.getRestaurantNumber()) {
                        b = false;
                        break;
                    }
                }
                if(b){
                    RestaurantInfoHandler.addRestaurantInfo(restaurant);
                    try {
                        RestaurantInfoHandler.saveCurrentRestaurants(openFileOutput(Config.FILE_FAV_PLACES, MODE_PRIVATE));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else Toast.makeText(this, "Restaurant already is a favourite", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(FavPlacesActivity.this, "No Restaurant found with this name", Toast.LENGTH_LONG).show();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String errorMassage) {
        Toast.makeText(FavPlacesActivity.this, "An error occurred while searching for the restaurant", Toast.LENGTH_LONG).show();
    }
}