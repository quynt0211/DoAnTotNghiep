package com.quynt.hethonghotrovanchuyen.model;

import com.google.gson.annotations.SerializedName;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 05/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class Owner {


    @SerializedName("idowner")
    private int id;

    @SerializedName("Name")
    private String mName;

    @SerializedName("PhoneNumber")
    private String mPhoneNumber;

    @SerializedName("Address")
    private String mAddress;

    @SerializedName("Password")
    private String mPassword;

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;

    }
}
