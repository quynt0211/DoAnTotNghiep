package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.DeliveryShipperAdapter;

import butterknife.Bind;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 20/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class DeliveryShipperActivity extends BaseActivity {
    @Bind(R.id.auction_shipper_list)
    protected ListView mDeliveryShipperList;

    @Override
    protected int getContentView() {
        return R.layout.activity_delivery_shipper;
    }

    @Override
    protected void initView() {
        setupList();
    }

    private void setupList() {
        DeliveryShipperAdapter deliveryShipperAdapter = new DeliveryShipperAdapter(this);
        mDeliveryShipperList.setAdapter(deliveryShipperAdapter);
    }
}
