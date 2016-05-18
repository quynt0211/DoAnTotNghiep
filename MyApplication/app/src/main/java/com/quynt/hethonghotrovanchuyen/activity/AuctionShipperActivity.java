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
import com.quynt.hethonghotrovanchuyen.adapter.AuctionShipperAdapter;
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
 * <p/>
 * Created by QuyNT on 12/03/2016.
 */
public class AuctionShipperActivity extends BaseActivity implements AuctionShipperAdapter.OnClickUpdateAuction {
    @Bind(R.id.auction_shipper)
    protected ListView mAuctionShipper;
    AuctionShipperAdapter auctionShipperAdapter;

    @Bind(R.id.auction_shipper_empty)
    TextView mAuctionShipperEmpty;

    @Override
    protected int getContentView() {
        return R.layout.activity_auction_shipper;
    }

    @Override
    protected void initView() {
        setupListView();
        getAuctionShipper();
    }


    private void setupListView() {
        auctionShipperAdapter = new AuctionShipperAdapter(this);
        auctionShipperAdapter.setOnClickUpdateAuction(this);
        mAuctionShipper.setAdapter(auctionShipperAdapter);
    }

    public void getAuctionShipper() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();

        params.put("id", String.valueOf(APIClient.getShipperAccount(this).getId()));
        Log.d("params_id_auction", String.valueOf(APIClient.getShipperAccount(this).getId()));

        APIClient.getInstance().execPost("viewAuctionShipper", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AuctionShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AuctionShipperActivity.this.runOnUiThread(new Runnable() {
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

                        AuctionShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int size = receiveDeliveryResponse.getmReceiveDeliveries().size();
                                if (size == 0) {
                                    mAuctionShipperEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    mAuctionShipperEmpty.setVisibility(View.GONE);
                                }
                                auctionShipperAdapter.setmReceiveDeliveries(receiveDeliveryResponse.getmReceiveDeliveries());
                            }
                        });
                    } else {
                        AuctionShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(AuctionShipperActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void clickUpdateAuction(ReceiveDelivery receiveDelivery) {
        Intent intent = new Intent(this, AuctionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("receive_delivery", receiveDelivery);
        intent.putExtras(bundle);
        startActivityForResult(intent, Const.AUCTION_SHIPPER_AUCTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.AUCTION_SHIPPER_AUCTION) {
            if (resultCode == Activity.RESULT_OK) {
                getAuctionShipper();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
