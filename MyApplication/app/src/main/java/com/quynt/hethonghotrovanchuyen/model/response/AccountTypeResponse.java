package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 07/05/2016.
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
