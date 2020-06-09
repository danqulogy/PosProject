package com.htlgrieskirchen.posproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.beans.RestaurantInfo;

import java.util.ArrayList;
import java.util.List;

public class FavPlacesLVAdapter extends BaseAdapter {

    List<RestaurantInfo> restaurants;
    int layoutId;
    LayoutInflater layoutInflater;

    public FavPlacesLVAdapter(Context context, int layoutId, List<RestaurantInfo> restaurants){
        this.restaurants = restaurants;
        this.layoutId = layoutId;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RestaurantInfo restaurant = restaurants.get(position);

        View listItem = (convertView == null)? layoutInflater.inflate(this.layoutId, null): convertView;
        ((TextView) listItem.findViewById(R.id.fav_places_item_tv)).setText(restaurant.getName());

        return listItem;
    }
}
