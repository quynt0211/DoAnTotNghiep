package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Owner;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 13/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class InfoPackageResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private PackageModel mPackage;

    public PackageModel getPackage(){
        return mPackage;
    }
}
