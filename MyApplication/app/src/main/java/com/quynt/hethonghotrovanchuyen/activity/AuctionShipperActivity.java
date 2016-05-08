package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AuctionShipperAdapter;

import butterknife.Bind;
/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
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
