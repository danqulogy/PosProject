package com.htlgrieskirchen.posproject.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Reservation;
import com.htlgrieskirchen.posproject.beans.Restaurant;

import java.util.List;

public class ReservationsLVAdapter extends BaseAdapter {

    List<Reservation> reservations;
    int layoutId;
    LayoutInflater inflater;

    public ReservationsLVAdapter(Context context, int layoutId, List<Reservation> reservations){
        this.reservations = reservations;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reservation reservation = reservations.get(position);

        View listItem = (convertView == null)? inflater.inflate(this.layoutId, null): convertView;
        ((TextView) listItem.findViewById(R.id.reservations_lv_item_tv)).setText(reservation.getId());

        return listItem;
    }
}
