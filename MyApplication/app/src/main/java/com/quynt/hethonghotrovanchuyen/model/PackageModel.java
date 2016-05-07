package com.quynt.hethonghotrovanchuyen.model;

import com.google.gson.annotations.SerializedName;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 07/05/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class PackageModel {

    @SerializedName("idpackage")
    private int mIdPackage;

    @SerializedName("idowner")
    private int mIdOwner;

    @SerializedName("name")
    private String mPackageName;

    @SerializedName("isfragile")
    private int mFragile;

    @SerializedName("isinflammable")
    private int mInFlammable;

    @SerializedName("isbulky")
    private int mBulky;

    @SerializedName("isheavy")
    private int mHeavy;

    @SerializedName("issamples")
    private int mSample;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("createtime")
    private String mCreateTime;

    @SerializedName("startlocation")
    private String mStartLocation;

    @SerializedName("endlocation")
    private String mEndLocation;

    @SerializedName("number")
    private int mNumber;

    @SerializedName("weigh")
    private double mWeigh;

    @SerializedName("currentlocation")
    private String mCurrentLocation;

    @SerializedName("idshipper")
    private int mIdShipper;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("updatedtime")
    private String mUpdateTime;

    @SerializedName("isDelivery")
    private int mIsDelivery;

    @SerializedName("ownerName")
    private String mOwnerName;

    @SerializedName("nameshipper")
    private String mShipperName;

    public int getmIdOwner() {
        return mIdOwner;
    }

    public void setmIdOwner(int mIdOwner) {
        this.mIdOwner = mIdOwner;
    }

    public int getmIdPackage() {
        return mIdPackage;
    }

    public void setmIdPackage(int mIdPackage) {
        this.mIdPackage = mIdPackage;
    }

    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public boolean isFragile() {
        return mFragile == 1;
    }

    public void setmFragile(int mFragile) {
        this.mFragile = mFragile;
    }

    public boolean isFlammable() {
        return mInFlammable == 1;
    }

    public void setmInFlammable(int mInFlammable) {
        this.mInFlammable = mInFlammable;
    }

    public boolean isBulky() {
        return mBulky == 1;
    }

    public void setmBulky(int mBulky) {
        this.mBulky = mBulky;
    }

    public boolean isHeavy() {
        return mHeavy == 1;
    }

    public void setmHeavy(int mHeavy) {
        this.mHeavy = mHeavy;
    }

    public boolean isSample() {
        return mSample == 1;
    }

    public void setmSample(int mSample) {
        this.mSample = mSample;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public String getmStartLocation() {
        return mStartLocation;
    }

    public void setmStartLocation(String mStartLocation) {
        this.mStartLocation = mStartLocation;
    }

    public String getmEndLocation() {
        return mEndLocation;
    }

    public void setmEndLocation(String mEndLocation) {
        this.mEndLocation = mEndLocation;
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public double getmWeigh() {
        return mWeigh;
    }

    public String getmCurrentLocation() {
        return mCurrentLocation;
    }

    public void setmCurrentLocation(String mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
    }

    public int getmIdShipper() {
        return mIdShipper;
    }

    public void setmIdShipper(int mIdShipper) {
        this.mIdShipper = mIdShipper;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmUpdateTime() {
        return mUpdateTime;
    }

    public void setmUpdateTime(String mUpdateTime) {
        this.mUpdateTime = mUpdateTime;
    }

    public boolean isDelivery() {
        return mIsDelivery == 1;
    }

    public void setmIsDelivery(int mIsDelivery) {
        this.mIsDelivery = mIsDelivery;
    }

    public String getmOwnerName() {
        return mOwnerName;
    }

    public void setmOwnerName(String mOwnerName) {
        this.mOwnerName = mOwnerName;
    }

    public String getShipperName(){
        return mShipperName;
    }
}
