package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AuctionOwnerAdapter;

import butterknife.Bind;

/**
 * PRESENA_Android
 * <p>
 * Created by Paditech on 27/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class AuctionOwnerActivity extends BaseActivity {
    @Bind(R.id.auction_owner_listview)
    protected ListView mAuctionOwners;

    @Override
    protected int getContentView() {
        return R.layout.activity_auction_owner;
    }

    @Override
    protected void initView() {
initListView();
    }

    private void initListView() {
        AuctionOwnerAdapter auctionOwnerAdapter = new AuctionOwnerAdapter(this);
        mAuctionOwners.setAdapter(auctionOwnerAdapter);
    }
}
