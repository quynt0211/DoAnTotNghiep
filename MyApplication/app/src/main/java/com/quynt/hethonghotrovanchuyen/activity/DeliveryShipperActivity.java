package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.DeliveryShipperAdapter;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
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
