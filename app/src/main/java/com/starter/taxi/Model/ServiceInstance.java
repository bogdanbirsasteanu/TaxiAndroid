package com.starter.taxi.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceInstance {
    private static final String TAG = "ServiceInstance";

    private int mId;
    private int mNumber;
    private String mDriver;
    private boolean mReportedStatus;
    private boolean mIsFree;
    private long mLocationX;
    private long mLocationY;

    public ServiceInstance(JSONObject object) {
        try {
            mId = object.getInt("id");
            mNumber = object.getInt("number");
            mDriver = object.getString("driver_first_name") + object.getString("driver_last_name");
            mReportedStatus = object.getBoolean("reported");
            mIsFree = object.getBoolean("free");
            mLocationX = object.getLong("stationX");
            mLocationY = object.getLong("stationY");
        } catch (JSONException e) {
            Log.e(TAG, "createFromJsonObject: " + e.getLocalizedMessage() );
        }
    }

    public int getId() {
        return mId;
    }

    public int getNumber() {
        return mNumber;
    }

    public String getDriver() {
        return mDriver;
    }

    public boolean isReportedStatus() {
        return mReportedStatus;
    }

    public boolean isFree() {
        return mIsFree;
    }

    public long getLocationX() {
        return mLocationX;
    }

    public long getLocationY() {
        return mLocationY;
    }

    //  Compute the distance from station to client location  using a simple triangulation.
    public long getDistanceToLocation(long locationX, long locationY) {
        if (mIsFree) {
            long xDiff = Math.abs(mLocationX - locationX);
            long yDiff = Math.abs(mLocationY - locationY);

            return xDiff * xDiff + yDiff * yDiff;
        }

        return Long.MAX_VALUE;
    }
}
