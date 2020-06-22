package com.htlgrieskirchen.restaurantside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.htlgrieskirchen.restaurantside.activities.TableActivity;
import com.htlgrieskirchen.restaurantside.adapters.TableAdapter;
import com.htlgrieskirchen.restaurantside.beans.Reservation;
import com.htlgrieskirchen.restaurantside.beans.Restaurant;
import com.htlgrieskirchen.restaurantside.beans.Table;
import com.htlgrieskirchen.restaurantside.handlers.RestaurantHandler;
import com.htlgrieskirchen.restaurantside.interfaces.RestaurantCallback;
import com.htlgrieskirchen.restaurantside.tasks.RestaurantTask;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantCallback {

    private TableAdapter adapter;
    private ListView listView;
    private Restaurant restaurant;
    private List<Table> tables;
    private RestaurantCallback callback = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.main_lv);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, TableActivity.class);
            tables = RestaurantHandler.getRestaurant().getTables();
            intent.putExtra("table", tables.get(position));
            startActivity(intent);
        });

        RestaurantTask task = new RestaurantTask(callback);
        task.execute("GET");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId ==  R.id.main_menu_refresh){
            RestaurantTask task = new RestaurantTask(callback);
            task.execute("GET");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(String method, Restaurant restaurant) {
        if(method.equals("GET")){

            if(this.restaurant != null){
                Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
            }

            this.restaurant = restaurant;
            this.tables = this.restaurant.getTables();

            RestaurantHandler.setRestaurant(this.restaurant);

            adapter = new TableAdapter(this, R.layout.main_lv_item, this.tables);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }
}