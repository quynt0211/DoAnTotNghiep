package com.quynt.hethonghotrovanchuyen.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.activity.AuctionShipperActivity;
import com.quynt.hethonghotrovanchuyen.activity.DeliveryShipperActivity;
import com.quynt.hethonghotrovanchuyen.activity.LoginActivity;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 12/03/2016.
 */
public class ShipperOtherFragment extends BaseFragment {

    @Bind(R.id.shipper_other_delivery)
    protected Button mBtnDelivery;

    @Bind(R.id.shipper_other_auction)
    protected Button mBtnAuction;


    @Override
    protected int getContentView() {
        return R.layout.fragment_shipper_other_;
    }

    @Override
    protected void initView(View view) {

    }

    @OnClick(R.id.shipper_other_delivery)
    protected void goToGetDelivery() {
        Intent intent = new Intent(getBaseActivity(), DeliveryShipperActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.shipper_other_auction)
    protected void goToAuction() {
        Intent intent = new Intent(getBaseActivity(), AuctionShipperActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.shipper_other_logout)
    protected void logout() {
        APIClient.removeAccount(getBaseActivity());
        goToLogin();
    }

    private void goToLogin() {
        Intent intent = new Intent(getBaseActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
