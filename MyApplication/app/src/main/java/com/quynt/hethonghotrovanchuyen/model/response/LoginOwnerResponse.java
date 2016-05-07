package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Owner;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 07/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class LoginOwnerResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("accountType")
    private int mAccountType;

    public int getAccountType(){
        return mAccountType;
    }

    @SerializedName("data")
    private Owner owner;

    public Owner getOwner(){
        return owner;
    }
}
