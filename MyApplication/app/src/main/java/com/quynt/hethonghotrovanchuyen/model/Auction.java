package com.quynt.hethonghotrovanchuyen.model;

import com.google.gson.annotations.SerializedName;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 09/04/2016.
 */
public class Auction {
    @SerializedName("idshipper")
    private int id;

    @SerializedName("name")
    private String mName;

    @SerializedName("phonenumber")
    private String mPhoneNumber;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("introduce")
    private String mIntroduce;

    @SerializedName("isFragile")
    private int mFragile;

    @SerializedName("isInflammable")
    private int mInflammable;

    @SerializedName("isBulky")
    private int mBulky;

    @SerializedName("isHeavy")
    private int mHeavy;

    @SerializedName("idpackage")
    private int mIdPackage;

    @SerializedName("isSamples")
    private int mSample;

    @SerializedName("idowner")
    private int mIdOwner;

    @SerializedName("allowed")
    private int mAllowed;

    @SerializedName("auctiondate")
    private String mAuctionDate;

    @SerializedName("rate")
    private double mRate;

    public int getmIdPackage() {
        return mIdPackage;
    }

    public int getId() {
        return id;
    }

    public String getmName() {
        return mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmIntroduce() {
        return mIntroduce;
    }

    public boolean isFragile() {
        return mFragile == 1;
    }

    public boolean isInflammable() {
        return mInflammable == 1;
    }

    public boolean isBulky() {
        return mBulky == 1;
    }

    public boolean isHeavy() {
        return mHeavy == 1;
    }

    public int getmIdOwner() {
        return mIdOwner;
    }

    public int getmAllowed() {
        return mAllowed;
    }

    public String getmAuctionDate() {
        return mAuctionDate;
    }

    public double getmRate() {
        return mRate;
    }

    public boolean isSample(){
        return mSample == 1;
    }



}
