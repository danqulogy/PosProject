package com.htlgrieskirchen.posproject.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RestaurantTask extends AsyncTask<String, String, List<Restaurant>> {

    private CallbackRestaurant callback;

    public RestaurantTask(CallbackRestaurant callback) {
        this.callback = callback;
    }

    @Override
    protected List<Restaurant> doInBackground(String... strings) {
        String method = strings[0];

        if(method.equals("NEAREST")){
            String distance = "";
            if(strings[3] != null && !strings[3].isEmpty()){
                distance = Config.RESTAURANT_URL_DISTANCE+strings[3];
            }
            try{
                Log.d("doInBackground", "Opening connection");
                URL url = new URL(Config.SERVER_URL+Config.RESTAURANT_NEAREST_URL1+strings[1]+Config.RESTAURANT_NEAREST_URL2+strings[2]+distance);
                Log.d("doInBackground", "URL: "+url.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Log.d("doInBackground", "finished Opening connection");

                int x;
                InputStream is = con.getInputStream();
                StringBuilder sb = new StringBuilder();
                while ((x = is.read()) != -1){
                    sb.append((char) x);
                }

                Gson gson = new Gson();
                TypeToken<List<Restaurant>> typeToken = new TypeToken<List<Restaurant>>(){};
                List<Restaurant> restaurants = gson.fromJson(sb.toString(), typeToken.getType());

                return restaurants;
            }catch (Exception e){
                Log.e("doInBackground-nearestRestaurant", "GETTING failed with connection; Error-Massage: "+e.getMessage());
            }
        }



        return null;
    }

    @Override
    public void onPostExecute(List<Restaurant> restaurants){
        if(restaurants != null){
            this.callback.onSuccess(restaurants);
        }else{
            this.callback.onFailure();
        }
    }
}
