package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;
import com.quynt.hethonghotrovanchuyen.model.Auction;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 14/05/2016.
 */
public class DetailAuctionResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private Auction mAuction;

    public Auction getAuction() {
        return mAuction;
    }
}
