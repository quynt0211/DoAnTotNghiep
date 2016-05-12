package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Owner;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 07/05/2016.
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
