package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;

import java.util.List;

/**
 * PRESENA_Android
 * <p>
 * Created by Paditech on 08/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class OwnerHistoryResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<PackageModel> mPackages;

    public List<PackageModel> getPackage(){
        return mPackages;
    }
}
