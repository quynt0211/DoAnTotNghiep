package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Shipper;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 07/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class LoginShipperResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("accountType")
    private int mAccountType;

    @SerializedName("data")
    private Shipper shipper;

    public Shipper getShipper(){
        return shipper;
    }
}
