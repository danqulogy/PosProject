package com.htlgrieskirchen.posproject.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.interfaces.CallbackReservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
            case "GET":
                try {
                    Log.d("doInBackground", "Opening connection");
                    URL url = new URL(Config.SERVER_URL + Config.RESERVATION_BY_ID_URL + strings[1]);
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
                    TypeToken<Reservation> typeToken = new TypeToken<Reservation>() {
                    };
                    return gson.fromJson(sb.toString(), typeToken.getType());

                } catch (Exception e) {
                    Log.e("doInBackground-nearestRestaurant", "GETTING failed with connection; Error-Massage: " + e.getMessage());
                    e.printStackTrace();
                }
                break;


            case "PUT":
                try {
                    URL url = new URL(Config.SERVER_URL + Config.RESERVATION_ADD_URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("PUT");

                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
                    bw.write(strings[1]);
                    bw.flush();
                    bw.close();

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while (line != null) {
                        line = br.readLine();
                        stringBuilder.append(line);
                    }

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                        @Override
                        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                        }
                    });
                    Gson gson = gsonBuilder.create();
                    //VERARBEITUNG VON REQUEST RETURN

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "CHECKRESERVATION":
                try {
                    URL url = new URL(Config.SERVER_URL + Config.RESERVATION_BY_ID_URL + strings[1]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();

                    int x;
                    InputStream is = con.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    while ((x = is.read()) != -1) {
                        sb.append((char) x);
                    }
                    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("d.M.yyyy HH:mm"))).create();
                    TypeToken<Reservation> typeToken = new TypeToken<Reservation>() {
                    };
                    return gson.fromJson(sb.toString(), typeToken.getType());

                } catch (Exception e) {

                }
                break;
            case "DELETE":
                try {
                    Log.d("doInBackground", "Opening connection");
                    URL url = new URL(Config.SERVER_URL + Config.RESERVATION_DELETE_URL + strings[1]);
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
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        outputReservation.setTableNumber(-1);
                        outputReservation.setId(strings[1]);
                    }

                    return outputReservation;
                } catch (Exception e) {
                    Log.e("doInBackground-nearestRestaurant", "GETTING failed with connection; Error-Massage: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }

    @Override
    public void onPostExecute(Reservation reservation) {
        if (reservation != null) {
            callback.onSuccess(method, reservation);
        } else callback.onFailure("An error occurred please try again!");
    }
}
