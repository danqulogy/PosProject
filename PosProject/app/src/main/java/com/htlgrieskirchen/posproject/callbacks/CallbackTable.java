package com.htlgrieskirchen.posproject.callbacks;

import com.htlgrieskirchen.posproject.beans.Table;

import java.util.List;

public interface CallbackTable {

    void onSuccess(List<Table> tables);
    void onFailure();
}
