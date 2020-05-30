package com.htlgrieskirchen.posproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htlgrieskirchen.posproject.R;
import com.htlgrieskirchen.posproject.beans.Restaurant;

public class DetailFragment extends Fragment {

    private TextView tvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initializeView(view);

        return view;
    }

    private void initializeView(View view){
        tvName = view.findViewById(R.id.detail_fragment_name);
    }

    public void showInformation(Restaurant restaurant){
        tvName.setText(restaurant.getName());
    }
}