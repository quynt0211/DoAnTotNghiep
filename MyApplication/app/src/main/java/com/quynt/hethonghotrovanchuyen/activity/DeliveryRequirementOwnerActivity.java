package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.DeliveryRequirementOwnerAdapter;

import butterknife.Bind;

/**
 * PRESENA_Android
 * <p>
 * Created by Paditech on 27/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class DeliveryRequirementOwnerActivity extends BaseActivity {
    @Bind(R.id.delivery_requirement_owner_list_view)
    protected ListView mDeliveryRequirementList;


    @Override
    protected int getContentView() {
        return R.layout.activity_delivery_requirement_owner;
    }

    @Override
    protected void initView() {
        initListView();
    }

    private void initListView() {
        DeliveryRequirementOwnerAdapter deliveryRequirementOwnerAdapter = new DeliveryRequirementOwnerAdapter(this);
        mDeliveryRequirementList.setAdapter(deliveryRequirementOwnerAdapter);
    }


}
