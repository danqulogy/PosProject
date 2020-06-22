package com.htlgrieskirchen.restaurantside.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.htlgrieskirchen.restaurantside.R;
import com.htlgrieskirchen.restaurantside.beans.Table;

import java.util.List;

public class TableAdapter extends BaseAdapter {

    List<Table> tables;
    int layoutId;
    LayoutInflater inflater;
    Context context;

    public TableAdapter(Context context, int layoutId, List<Table> tables) {
        this.tables = tables;
        this.layoutId = layoutId;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public Object getItem(int position) {
        return tables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Table table = tables.get(position);

        Resources resources = context.getResources();

        View listItem = (convertView == null)? inflater.inflate(this.layoutId, null): convertView;
        ((TextView) listItem.findViewById(R.id.main_lv_item_table)).setText(String.format(resources.getString(R.string.tableItem), table.getId()));
        ((TextView) listItem.findViewById(R.id.main_lv_item_persons_amount)).setText(String.valueOf(table.getChairsAvailable()));

        return listItem;
    }
}
