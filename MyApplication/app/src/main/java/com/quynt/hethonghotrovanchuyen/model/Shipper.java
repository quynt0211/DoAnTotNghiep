package com.quynt.hethonghotrovanchuyen.model;

import com.google.gson.annotations.SerializedName;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
 */
public class Shipper {
    @SerializedName("idshipper")
    private int id;

    @SerializedName("name")
    private String mName;

    @SerializedName("phonenumber")
    private String mPhoneNumber;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("introduce")
    private String mIntroduce;

    @SerializedName("isFragile")
    private int mFragile;

    @SerializedName("isInflammable")
    private int mInflammable;

    @SerializedName("isBulky")
    private int mBulky;

    @SerializedName("isHeavy")
    private int mHeavy;

    @SerializedName("isSamples")
    private int mSample;

    public int getId(){
        return id;
    }

    public String getName(){
        return mName;
    }

    public String getPhoneNumber(){
        return mPhoneNumber;
    }

    public String getmAddress(){
        return mAddress;
    }

    public String getmIntroduce(){
        return mIntroduce;
    }

    public boolean isFragile(){
        return mFragile == 1;
    }

    public boolean isInflammable(){
        return mInflammable == 1;
    }

    public boolean isBulky(){
        return mBulky == 1;
    }

    public boolean isHeavy(){
        return mHeavy == 1;
    }

    public boolean isSamples(){
        return mSample == 1;
    }
}
