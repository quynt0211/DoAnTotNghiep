package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 07/05/2016.
 */
public class ErrorResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public boolean hasError() {
        return !success;
    }

}
