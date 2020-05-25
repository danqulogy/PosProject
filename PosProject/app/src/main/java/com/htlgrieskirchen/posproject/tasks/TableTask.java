package com.htlgrieskirchen.posproject.tasks;

import android.os.AsyncTask;

import com.htlgrieskirchen.posproject.beans.Table;

import java.util.List;

public class TableTask extends AsyncTask<String, String, List<Table>> {


    @Override
    protected List<Table> doInBackground(String... strings) {
        return null;
    }

    @Override
    public void onPostExecute(List<Table> tables){

    }
}
