package com.htlgrieskirchen.restaurantside;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.htlgrieskirchen.restaurantside.adapters.TableAdapter;
import com.htlgrieskirchen.restaurantside.beans.Restaurant;
import com.htlgrieskirchen.restaurantside.beans.Table;
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

        RestaurantTask task = new RestaurantTask(callback);
        task.execute("GET");
    }


    @Override
    public void onSuccess(String method, Restaurant restaurant) {
        if(method.equals("GET")){
            this.restaurant = restaurant;
            this.tables = this.restaurant.getTables();

            adapter = new TableAdapter(this, R.layout.main_lv_item, this.tables);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }
}