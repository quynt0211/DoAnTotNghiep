package com.quynt.hethonghotrovanchuyen.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 05/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
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
