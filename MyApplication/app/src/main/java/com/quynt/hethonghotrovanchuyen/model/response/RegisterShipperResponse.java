package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Shipper;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 07/05/2016.
 */
public class RegisterShipperResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private Shipper mShipper;

    public Shipper getShipper() {
        return mShipper;
    }
}
