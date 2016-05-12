package com.quynt.hethonghotrovanchuyen.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 10/05/2016.
 */
public class ReceiveDelivery implements Serializable {

    @SerializedName("nameowner")
    private String mOwnerName;

    @SerializedName("idpackage")
    private int mIdPackage;

    public int getmIdPackage() {
        return mIdPackage;
    }

    @SerializedName("name")
    private String mPackageName;

    @SerializedName("startlocation")
    private String mStartLocation;

    @SerializedName("endlocation")
    private String mEndLocation;

    @SerializedName("allowed")
    private int mAllowed;

    @SerializedName("rate")
    private int mRate;

    public String getmOwnerName() {
        return mOwnerName;
    }

    public void setmOwnerName(String mOwnerName) {
        this.mOwnerName = mOwnerName;
    }

    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public String getmStartLocation() {
        return mStartLocation;
    }

    public void setmStartLocation(String mStartLocation) {
        this.mStartLocation = mStartLocation;
    }

    public String getmEndLocation() {
        return mEndLocation;
    }

    public void setmEndLocation(String mEndLocation) {
        this.mEndLocation = mEndLocation;
    }

    public boolean isAllowed() {
        return mAllowed == 1;
    }

    public int getRate() {
        return mRate;
    }

}
