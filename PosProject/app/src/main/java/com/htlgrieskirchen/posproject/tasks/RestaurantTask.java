package com.htlgrieskirchen.posproject.tasks;

import android.os.AsyncTask;

import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.callbacks.CallbackRestaurant;

import java.util.List;

public class RestaurantTask extends AsyncTask<String, String, List<Restaurant>> {

    private CallbackRestaurant callback;

    public RestaurantTask(CallbackRestaurant callback) {
        this.callback = callback;
    }

    @Override
    protected List<Restaurant> doInBackground(String... strings) {
        return null;
    }

    @Override
    public void onPostExecute(List<Restaurant> restaurants){
        this.callback.onSuccess(restaurants);
    }
}
