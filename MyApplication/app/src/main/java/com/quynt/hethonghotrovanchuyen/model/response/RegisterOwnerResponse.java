package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Owner;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 07/05/2016.
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
