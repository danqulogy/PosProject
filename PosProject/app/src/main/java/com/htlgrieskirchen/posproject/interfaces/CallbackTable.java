package com.htlgrieskirchen.posproject.interfaces;

import com.htlgrieskirchen.posproject.beans.Table;

import java.util.List;

public interface CallbackTable {

    void onSuccess(List<Table> tables);
    void onFailure();
}
