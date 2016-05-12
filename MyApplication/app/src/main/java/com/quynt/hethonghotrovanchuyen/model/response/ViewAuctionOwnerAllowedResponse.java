package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Auction;

import java.util.List;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 09/05/2016.
 */
public class ViewAuctionOwnerAllowedResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Auction> mAuctions;

    public List<Auction> getAuctions() {
        return mAuctions;
    }
}
