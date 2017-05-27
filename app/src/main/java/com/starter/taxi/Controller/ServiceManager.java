package com.starter.taxi.Controller;

import android.util.Log;

import com.starter.taxi.Model.ServiceInstance;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ServiceManager {

    //region GLOBALS

    private static final String TAG = "ServiceManager";

    private static ServiceManager mInstance = new ServiceManager();
    private ArrayList<ServiceInstance> mServices = new ArrayList<ServiceInstance>();

    //endregion

    private ServiceManager() {
        mServices = new ArrayList<ServiceInstance>();
    }

    public static ServiceManager getInstance() {
        if(mInstance == null) {
            mInstance = new ServiceManager();
        }
        return mInstance;
    }

    public synchronized void setServices(JSONArray array) {
        if(mServices == null || mServices.size() != 0) {
            mServices = new ArrayList<ServiceInstance>();
        }

        for(int i = 0; i < array.length(); i++) {
            try {
                mServices.add(new ServiceInstance(array.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e(TAG, "setServices: " + e.getLocalizedMessage() );
            }
        }

        sort();
    }

    private void sort() {
        Collections.sort(mServices, new Comparator<ServiceInstance>() {
            @Override
            public int compare(ServiceInstance serviceInstance, ServiceInstance t1) {
                long locX1 = serviceInstance.getLocationX();
                long locX2 = t1.getLocationX();

                return (int) (locX1 > locX2 ? locX1 - locX2 : locX2 - locX1);
            }
        });

        for(ServiceInstance serviceInstance: mServices) {
            Log.d(TAG, "sort: " + serviceInstance.getId() + " x:" + serviceInstance.getLocationX());
        }
    }

    public synchronized int getClosestServiceId() {
        //TODO get device location X Y
        long clientLocationX = 1;
        long clientLocationY = 1;

        ServiceInstance closestService = null;
        long distance = Long.MAX_VALUE;
        for(ServiceInstance s: mServices) {
            if(!s.isFree()) continue;

            long newDistance = s.getDistanceToLocation(clientLocationX, clientLocationY);
            if(newDistance < distance) {
                closestService = s;
                distance = newDistance;
            }
        }

        return closestService.getId();
    }
}
