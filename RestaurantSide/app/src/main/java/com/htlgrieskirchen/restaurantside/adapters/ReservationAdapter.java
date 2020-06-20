package com.htlgrieskirchen.restaurantside.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htlgrieskirchen.restaurantside.R;
import com.htlgrieskirchen.restaurantside.beans.Reservation;
import com.htlgrieskirchen.restaurantside.beans.Table;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ReservationAdapter extends BaseAdapter {

    List<Reservation> reservations;
    int layoutId;
    LayoutInflater inflater;

    public ReservationAdapter(Context context, int layoutId, List<Reservation> reservations) {
        this.reservations = reservations;
        this.layoutId = layoutId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");

        Reservation reservation = reservations.get(position);

        View listItem = (convertView == null)? inflater.inflate(this.layoutId, null): convertView;
        ((TextView) listItem.findViewById(R.id.reservation_lv_item_name)).setText(reservation.getName());
        ((TextView) listItem.findViewById(R.id.reservation_lv_item_date)).setText(reservation.getReservationStart().toLocalDate().format(date));
        ((TextView) listItem.findViewById(R.id.reservation_lv_item_start)).setText(reservation.getReservationStart().toLocalTime().format(time));
        ((TextView) listItem.findViewById(R.id.reservation_lv_item_end)).setText(reservation.getReservationEnd().toLocalTime().format(time));

        return listItem;
    }
}
