package com.htlgrieskirchen.posproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.adapters.MainLVAdapter;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.interfaces.OnSelectionChangedListener;

import java.util.List;

public class MainFragment extends Fragment {

    private ListView listView;
    private List<Restaurant> restaurants;
    private OnSelectionChangedListener listener;
    private MainLVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeView(view);

        return view;
    }

    private void initializeView(View givenView) {
        listView = givenView.findViewById(R.id.main_fragment_listview);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Restaurant restaurant = restaurants.get(position);
            listener.onSelectionChanged(restaurant);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectionChangedListener) {
            listener = (OnSelectionChangedListener) context;
        } else {
            Log.e("MainFragment-onAttach", "Activity does not implement OnSelectionChangedListener");
        }
    }

    public void updateLV(List<Restaurant> restaurants) {
        this.restaurants = restaurants;

        adapter = new MainLVAdapter(requireActivity(), R.layout.main_fragment_lv_item, this.restaurants);
        listView.setAdapter(adapter);
    }
}