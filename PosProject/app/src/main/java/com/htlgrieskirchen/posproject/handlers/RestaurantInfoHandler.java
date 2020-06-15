package com.htlgrieskirchen.posproject.handlers;

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
import java.util.ArrayList;
import java.util.List;

public class RestaurantInfoHandler {

    private static  List<RestaurantInfo> restaurantInfos = new ArrayList<>();

    public static void safeCurrentRestaurants(OutputStream outputStream){
        try(PrintWriter pw = new PrintWriter(outputStream)){
            Gson gson = new Gson();
            List<RestaurantInfo> outputList = new ArrayList<>();

            for(RestaurantInfo r: restaurantInfos){
                outputList.add(new RestaurantInfo(r.getDbId(), r.getName()));
            }

            String json = gson.toJson(outputList);
            pw.write(json);
            pw.flush();
        }
    }

    public static void loadCurrentRestaurants(InputStream is){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            StringBuilder input = new StringBuilder();
            while(br.ready()){
                input.append(br.readLine());
            }
            Gson gson = new Gson();
            TypeToken<List<RestaurantInfo>> typeToken = new TypeToken<List<RestaurantInfo>>(){};

            restaurantInfos = gson.fromJson(input.toString(), typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRestaurantInfo(Restaurant restaurant){
        restaurantInfos.add(new RestaurantInfo(restaurant.getId(), restaurant.getName(), restaurant));
    }

    public static void deleteRestaurantInfo(RestaurantInfo info){
        for(RestaurantInfo r: restaurantInfos){
            if(r.getDbId().equals(info.getDbId())) restaurantInfos.remove(r);
        }
    }

    public static void addRestaurantToInfo(Restaurant restaurant){
        for(RestaurantInfo r: restaurantInfos){
            if(r.getDbId().equals(restaurant.getId())) r.setRestaurant(restaurant);
        }
    }

    public static List<RestaurantInfo> getRestaurantInfos() {
        return restaurantInfos;
    }

    public static void setRestaurantInfos(List<RestaurantInfo> restaurantInfos) {
        RestaurantInfoHandler.restaurantInfos = restaurantInfos;
    }
}
