package com.htlgrieskirchen.restaurantside.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.htlgrieskirchen.restaurantside.Config;
import com.htlgrieskirchen.restaurantside.beans.Reservation;
import com.htlgrieskirchen.restaurantside.interfaces.CallbackReservation;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationTask extends AsyncTask<String, String, Reservation> {

    String method;
    CallbackReservation callback;

    public ReservationTask(CallbackReservation callback) {
        this.callback = callback;
    }

    @Override
    protected Reservation doInBackground(String... strings) {
        method = strings[0];

        switch (method) {
            case "DELETE":
                try {
                    Log.d("doInBackground", "Opening connection");
                    URL url = new URL(Config.SERVER_URL + Config.RESERVATION_DELETE_URL+strings[1]);
                    Log.d("doInBackground", "URL: " + url.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("DELETE");
                    Log.d("doInBackground", "finished Opening connection");
                    int responseCode = con.getResponseCode();

                    int x;
                    InputStream is = con.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    while ((x = is.read()) != -1) {
                        sb.append((char) x);
                    }

                    Reservation outputReservation = new Reservation();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        outputReservation.setTableNumber(-1);
                        outputReservation.setId(strings[1]);
                    }

                    return outputReservation;
                } catch (Exception e) {
                    Log.e("doInBackground-nearestRestaurant", "GETTING failed with connection; Error-Massage: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "PUT":
                try {
                    Log.d("doInBackground", "Opening connection");
                    URL url = new URL(Config.SERVER_URL + Config.RESERVATION_ADD_URL);
                    Log.d("doInBackground", "URL: " + url.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("PUT");
                    con.setDoOutput(true);
                    Log.d("doInBackground", "finished Opening connection");

                    Log.d("doInBackground", strings[1]);

                    PrintWriter pw = new PrintWriter(con.getOutputStream());
                    pw.write(strings[1]);
                    pw.flush();

                    int responseCode = con.getResponseCode();

                    return (responseCode == HttpURLConnection.HTTP_OK)? new Reservation(): null;
                } catch (Exception e) {
                    Log.e("doInBackground-nearestRestaurant", "GETTING failed with connection; Error-Massage: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }

    @Override
    public void onPostExecute(Reservation reservation){
        if(reservation != null){
            callback.onSuccess(method, reservation);
        }else callback.onFailure("An error occurred please try again!");
    }
}
