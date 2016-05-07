package com.quynt.hethonghotrovanchuyen.model;

import com.google.gson.annotations.SerializedName;

/**
 * PRESENA_Android
 * <p>
 * Created by Paditech on 05/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
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
