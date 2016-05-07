package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Owner;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 05/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class RegisterOwnerResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("owner")
    private Owner mOwner;

    public Owner getOwner() {
        return mOwner;
    }
}
