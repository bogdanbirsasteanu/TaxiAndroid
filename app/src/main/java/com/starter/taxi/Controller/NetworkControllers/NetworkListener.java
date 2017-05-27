package com.starter.taxi.Controller.NetworkControllers;

import org.json.JSONArray;

public interface NetworkListener {
    void onFailed(String e);
    void onSuccess(JSONArray array);
}
