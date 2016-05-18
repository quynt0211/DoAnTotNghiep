package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.DeliveryShipperAdapter;
import com.quynt.hethonghotrovanchuyen.model.ReceiveDelivery;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.ReceiveDeliveryResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.Const;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 12/03/2016.
 */
public class DeliveryShipperActivity extends BaseActivity implements DeliveryShipperAdapter.OnClickAuctionButtonListenner {
    @Bind(R.id.auction_shipper_list)
    protected ListView mDeliveryShipperList;

    DeliveryShipperAdapter deliveryShipperAdapter;

    @Bind(R.id.delivery_shipper_empty)
    TextView mDeliveryShipperEmpty;

    @Override
    protected int getContentView() {
        return R.layout.activity_delivery_shipper;
    }

    @Override
    protected void initView() {
        setupList();
        getReceiveDelivery();
    }

    private void setupList() {
        deliveryShipperAdapter = new DeliveryShipperAdapter(this);
        deliveryShipperAdapter.setOnClickAuctionButtonListenner(this);
        mDeliveryShipperList.setAdapter(deliveryShipperAdapter);
    }

    private void getReceiveDelivery() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("id", String.valueOf(APIClient.getShipperAccount(this).getId()));
        Log.d("param_id", String.valueOf(APIClient.getShipperAccount(this).getId()));

        APIClient.getInstance().execPost("viewDelivery", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DeliveryShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DeliveryShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        final ReceiveDeliveryResponse receiveDeliveryResponse = new Gson().fromJson(body, ReceiveDeliveryResponse.class);
                        DeliveryShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int size = receiveDeliveryResponse.getmReceiveDeliveries().size();
                                if(size == 0 ){
                                    mDeliveryShipperEmpty.setVisibility(View.VISIBLE);
                                }else{
                                    mDeliveryShipperEmpty.setVisibility(View.GONE);
                                }
                                deliveryShipperAdapter.setmReceiveDeliveries(receiveDeliveryResponse.getmReceiveDeliveries());
                            }
                        });
                    } else {
                        DeliveryShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(DeliveryShipperActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.DELIVERY_SHIPPER_AUCTION) {
            if (resultCode == Activity.RESULT_OK) {
                getReceiveDelivery();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void clickAuctionButton(ReceiveDelivery receiveDelivery) {
        Intent intent = new Intent(this, AuctionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("receive_delivery", receiveDelivery);
        intent.putExtras(bundle);
        startActivityForResult(intent, Const.DELIVERY_SHIPPER_AUCTION);
    }
}
