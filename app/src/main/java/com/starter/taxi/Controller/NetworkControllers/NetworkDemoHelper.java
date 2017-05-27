package com.starter.taxi.Controller.NetworkControllers;

import android.content.Context;
import android.util.Log;

import com.starter.taxi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class NetworkDemoHelper {

    private static final String TAG = "NetworkDemoHelper";

    static class ResponseData<T> {
        private T body;
        private int code;
        private String message;

        ResponseData(T body, int code, String message) {
            this.body = body;
            this.code = code;
            this.message = message;
        }

        T getBody() {
            return body;
        }

        int getCode() {
            return code;
        }

        String getMessage() {
            return message;
        }
    }

    public NetworkDemoHelper() {
    }

    static ResponseData<JSONArray> getDemoServices(Context context) {
        int code = -1;
        String message = "Processing";
        JSONArray body = new JSONArray();

        InputStream is = context.getResources().openRawResource(R.raw.json_file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String buffer = "";
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                buffer += line;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading from raw: " + e.getLocalizedMessage() );
        }

        if(buffer.length() == 0) {
            Log.e(TAG, "Reading error occurred: buffer.length == " + buffer.length() );
        }
        try {
            JSONObject jsonData = new JSONObject(buffer);
            body = jsonData.getJSONArray("services");
            code = 200;
            message = "Success";
        } catch (JSONException e) {
            Log.e(TAG, "Parse error: " + e.getLocalizedMessage() );
            code = 201;
            message = e.getLocalizedMessage();
        }

        return new ResponseData<JSONArray>(body, code, message);
    }
}
