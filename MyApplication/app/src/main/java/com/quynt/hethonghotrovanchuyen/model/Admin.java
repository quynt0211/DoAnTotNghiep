package com.quynt.hethonghotrovanchuyen.model;

import com.google.gson.annotations.SerializedName;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 14/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class Admin {
    @SerializedName("id")
    private String mIdAdmin;

    @SerializedName("password")
    private String mPassword;

    public String getIdAdmin(){
        return mIdAdmin;
    }

    public String getPassword(){
        return mPassword;
    }
}
