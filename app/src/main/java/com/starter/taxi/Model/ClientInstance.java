package com.starter.taxi.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientInstance {
    private static final String TAG = "ClientInstance";
    private static ClientInstance mInstance = new ClientInstance();

    private int mId;
    private String mFirstName;
    private String mLastName;
    private String mPhone;


    private ClientInstance() {
        mId = -1;
        mFirstName = "";
        mLastName = "";
        mPhone = "";
    }

    public static ClientInstance getInstance() {
        if(mInstance == null) {
            mInstance = new ClientInstance();
        }

        return mInstance;
    }

    private void createFromJsonObject(JSONObject object) {
        try {
            mId = object.getInt("id");
            mFirstName = object.getString("client_first_name");
            mLastName = object.getString("client_last_name");
            mPhone = object.getString("phone");
        } catch (JSONException e) {
            Log.e(TAG, "createFromJsonObject: " + e.getLocalizedMessage());
        }
    }
}
