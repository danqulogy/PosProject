package com.htlgrieskirchen.posproject.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.Config;
import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.adapters.MainLVAdapter;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;
import com.htlgrieskirchen.posproject.interfaces.OnSelectionChangedListener;
import com.htlgrieskirchen.posproject.settings.SettingsActivity;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {

    private ListView listView;
    private List<Restaurant> restaurants = new ArrayList<>();
    private OnSelectionChangedListener listener;
    private MainLVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeView(view);

        return view;
    }

    private void initializeView(View givenView){
        listView = givenView.findViewById(R.id.main_fragment_listview);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Restaurant restaurant = restaurants.get(position);
            listener.onSelectionChanged(restaurant);
        });
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof OnSelectionChangedListener){
            listener = (OnSelectionChangedListener) context;
        }else{
            Log.e("MainFragment-onAttach", "Activity does not implement OnSelectionChangedListener");
        }
    }

    public void updateLV(List<Restaurant> restaurants){
        if(restaurants == null || restaurants.size() == 0){
            this.restaurants = new ArrayList<>();
        }else this.restaurants = restaurants;

        if(adapter == null){
            adapter = new MainLVAdapter(requireActivity(), R.layout.main_fragment_lv_item, restaurants);
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
}