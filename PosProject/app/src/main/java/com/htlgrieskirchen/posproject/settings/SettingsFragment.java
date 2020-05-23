package com.htlgrieskirchen.posproject.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.htlgrieskirchen.posproject.R;

public class SettingsFragment extends PreferenceFragmentCompat{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
