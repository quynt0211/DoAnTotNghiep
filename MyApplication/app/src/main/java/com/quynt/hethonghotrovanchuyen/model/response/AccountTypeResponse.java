package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 07/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class AccountTypeResponse  {
    @SerializedName("success")
    private boolean success;

    @SerializedName("accountType")
    private int mAccountType;

    public int getAccountType(){
        return mAccountType;
    }
}
