package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Owner;

import java.util.List;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 15/05/2016.
 */
public class OwnerAccountInSystemResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Owner> mOwners;

    public List<Owner> getOwners(){
        return mOwners;
    }
}
