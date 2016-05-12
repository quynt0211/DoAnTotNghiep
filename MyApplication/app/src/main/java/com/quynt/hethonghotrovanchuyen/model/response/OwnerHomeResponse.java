package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;

import java.util.List;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 07/05/2016.
 */
public class OwnerHomeResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<PackageModel> mPackages;

    public List<PackageModel> getPackage(){
        return mPackages;
    }

}
