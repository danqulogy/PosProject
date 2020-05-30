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

import java.util.List;

public class MainFragment extends Fragment implements CallbackRestaurant {

    private CallbackRestaurant callback = this;
    private ListView listView;
    private List<Restaurant> restaurants;
    private OnSelectionChangedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initializeView(view);

        return view;
    }

    private void initializeView(View view){
        listView = view.findViewById(R.id.main_fragment_listview);
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
        MainLVAdapter adapter = new MainLVAdapter(getActivity(), R.layout.main_fragment_lv_item, restaurants);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> listener.onSelectionChanged(restaurants.get(position)));
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), "An issue with the download occurred", Toast.LENGTH_LONG).show();
    }
}