package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AuctionOwnerAdapter;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
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
