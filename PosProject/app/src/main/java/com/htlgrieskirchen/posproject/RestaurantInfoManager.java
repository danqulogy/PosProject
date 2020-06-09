package com.htlgrieskirchen.posproject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.beans.RestaurantInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class RestaurantInfoManager {

    public RestaurantInfoManager(){}

    public static void safeCurrentRestaurants(OutputStream outputStream, List<RestaurantInfo> restaurants){
        try(PrintWriter pw = new PrintWriter(outputStream)){
            Gson gson = new Gson();
            String json = gson.toJson(restaurants);
            pw.write(json);
            pw.flush();
        }
    }

    public static List<RestaurantInfo> loadCurrentRestaurants(InputStream is){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            StringBuilder input = new StringBuilder();
            while(br.ready()){
                input.append(br.readLine());
            }
            Gson gson = new Gson();
            TypeToken<List<RestaurantInfo>> typeToken = new TypeToken<List<RestaurantInfo>>(){};

            return gson.fromJson(input.toString(), typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
