package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Shipper;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 07/05/2016.
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
