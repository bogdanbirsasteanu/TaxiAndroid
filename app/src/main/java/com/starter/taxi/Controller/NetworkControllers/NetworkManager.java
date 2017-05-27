package com.starter.taxi.Controller.NetworkControllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.starter.taxi.Controller.NetworkUtils.NetworkUtils;
import com.starter.taxi.Controller.ServiceManager;
import com.starter.taxi.Model.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static NetworkManager mInstance = new NetworkManager();

    private OkHttpClient mClient;
    private NetworkListener mNetworkListener;

    private NetworkManager() {
        mClient = new OkHttpClient();
    }

    public static NetworkManager getInstance() {
        if(mInstance == null) {
            mInstance = new NetworkManager();
        }
        return mInstance;
    }

    public void setNetworkListener(NetworkListener listener) {
        mNetworkListener = listener;
    }

    public synchronized void getServices() {
        if(NetworkUtils.DEMO_ENVIRONMENT) {
            NetworkDemoHelper.ResponseData<JSONArray> data = NetworkDemoHelper.getDemoServices((Context) mNetworkListener);
            int code = data.getCode();
            switch (code) {
                case 200:
                    ServiceManager.getInstance().setServices(data.getBody());
                    mNetworkListener.onSuccess(data.getBody());
            }
            return;
        }

        mClient.newCall(new Request.Builder().url(NetworkUtils.SERVICES_URL).build()).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        String error = "Error: " + e.getLocalizedMessage();

                        Log.e(TAG,e.getLocalizedMessage());
                        mNetworkListener.onFailed(error);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        switch (response.code()) {
                            case CommonUtils.SUCCESS:
                                JSONArray jsonResponse;
                                try {
                                    jsonResponse = createResponse(response.body().string());
                                } catch (JSONException e) {
                                    String error = CommonUtils.PARSE_ERROR + ": " + e.getLocalizedMessage();
                                    mNetworkListener.onFailed(error);
                                    return;
                                }
                                mNetworkListener.onSuccess(jsonResponse);
                                break;

                            default:
                                String e = response.code() + ":" + response.body().string();
                                mNetworkListener.onFailed(e);
                        }
                    }
                }
        );
    }

    private synchronized JSONArray createResponse(@NonNull String data) throws JSONException {
        return new JSONArray(data);
    }
}
