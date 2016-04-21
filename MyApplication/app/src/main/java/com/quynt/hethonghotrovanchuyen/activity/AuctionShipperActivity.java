package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AuctionShipperAdapter;

import butterknife.Bind;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 21/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class AuctionShipperActivity extends BaseActivity {
    @Bind(R.id.auction_shipper)
    protected ListView mAuctionShipper;

    @Override
    protected int getContentView() {
        return R.layout.activity_auction_shipper;
    }

    @Override
    protected void initView() {
        setupListView();
    }


    private void setupListView() {
        AuctionShipperAdapter auctionShipperAdapter = new AuctionShipperAdapter(this);
        mAuctionShipper.setAdapter(auctionShipperAdapter);
    }
}
