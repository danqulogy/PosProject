package com.htlgrieskirchen.posproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.adapters.MainLVAdapter;
import com.htlgrieskirchen.posproject.beans.Restaurant;
import com.htlgrieskirchen.posproject.interfaces.CallbackRestaurant;
import com.htlgrieskirchen.posproject.interfaces.OnSelectionChangedListener;
import com.htlgrieskirchen.posproject.tasks.RestaurantTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements CallbackRestaurant {

    private CallbackRestaurant callback = this;
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
        RestaurantTask restaurantTask = new RestaurantTask(callback);
        restaurantTask.execute("NEAREST", "13.9", "48.0000", "10000");
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

    @Override
    public void onSuccess(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        adapter = new MainLVAdapter(requireActivity(), R.layout.main_fragment_lv_item, restaurants);
        listView.setAdapter(adapter);

    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), "An issue with the download occurred", Toast.LENGTH_LONG).show();
    }
}