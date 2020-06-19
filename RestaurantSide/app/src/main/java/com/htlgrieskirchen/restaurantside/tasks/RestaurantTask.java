package com.htlgrieskirchen.restaurantside.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.restaurantside.Config;
import com.htlgrieskirchen.restaurantside.beans.Restaurant;
import com.htlgrieskirchen.restaurantside.interfaces.RestaurantCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTask extends AsyncTask<String, String, Restaurant> {

    RestaurantCallback callback;
    String method;

    public RestaurantTask(RestaurantCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Restaurant doInBackground(String... strings) {
        method = strings[0];

        switch (method){
            case "GET":
                try {
                    Log.d("doInBackground", "Opening connection");
                    URL url = new URL(Config.SERVER_URL + Config.RESTAURANT_BY_RESTAURANT_NUMBER+ Config.RESTAURANT_NUMBER);
                    Log.d("doInBackground", "URL: " + url.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    Log.d("doInBackground", "finished Opening connection");

                    int x;
                    InputStream is = con.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    while ((x = is.read()) != -1) {
                        sb.append((char) x);
                    }

                    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("d.M.yyyy HH:mm"))).create();
                    TypeToken<Restaurant> typeToken = new TypeToken<Restaurant>() {};

                    return gson.fromJson(sb.toString(), typeToken.getType());
                } catch (IOException ex) {
                    Log.d("doInBackground", "Restaurant Number went wrong error massage: " + ex.getMessage());
                }
                break;
        }

        return null;
    }

    @Override
    public void onPostExecute(Restaurant restaurant){
        switch (method){
            case "GET":
                    if(restaurant == null) this.callback.onFailure("Something went wrong while downloading from server");
                    else this.callback.onSuccess(method, restaurant);
                break;
        }
    }
}
