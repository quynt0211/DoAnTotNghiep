package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Shipper;

import java.util.List;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 09/04/2016.
 */
public class ViewAuctionOwnerResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Shipper> mShipper;

    public List<Shipper>  getShipper() {
        return mShipper;
    }

}
