package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Admin;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 14/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class LoginAdminResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private Admin mAdmin;

    public Admin getAdmin() {
        return mAdmin;
    }
}
