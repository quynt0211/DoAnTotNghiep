package com.quynt.hethonghotrovanchuyen.activity;

import android.util.Log;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;

import butterknife.OnClick;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 27/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class CreateDeliveryRequirementActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_create_delivery_requirement;
    }

    @Override
    protected void initView() {
        Log.d("initView", "111");
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.create_delivery_root), this);
    }

    @OnClick(R.id.cancel_requirement)
    protected void cancel() {
        this.finish();
    }
}
