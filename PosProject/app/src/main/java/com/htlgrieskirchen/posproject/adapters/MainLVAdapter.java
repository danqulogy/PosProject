package com.htlgrieskirchen.posproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;

import java.util.List;

public class MainLVAdapter extends BaseAdapter {

    List<Restaurant> restaurants;
    int layoutId;
    LayoutInflater inflater;

    public MainLVAdapter(Context ctx, int layoutId, List<Restaurant> restaurants){
        this.restaurants = restaurants;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        Restaurant restaurant = restaurants.get(position);

        View listItem = (convertView == null)? inflater.inflate(this.layoutId, null): convertView;
        ((TextView) listItem.findViewById(R.id.main_fragment_lv_item_name)).setText(restaurant.getName());

        return listItem;
    }
}
