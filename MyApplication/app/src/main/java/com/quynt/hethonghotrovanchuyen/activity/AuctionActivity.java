package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.ReceiveDelivery;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.quynt.hethonghotrovanchuyen.utils.StringUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 10/05/2016.
 */
public class AuctionActivity extends BaseActivity {

    @Bind(R.id.auction_owner_name)
    TextView mOwnerName;

    @Bind(R.id.auction_package_name)
    TextView mPackageName;

    @Bind(R.id.auction_start_location)
    TextView mStartLocation;

    @Bind(R.id.auction_end_location)
    TextView mEndLocation;

    @Bind(R.id.auction_rate)
    EditText mRateExt;
    private int mRate;

    ReceiveDelivery receiveDelivery;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int TIME_OUT = 3000;

    @Override
    protected int getContentView() {
        return R.layout.activity_auction;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            receiveDelivery = (ReceiveDelivery) bundle.getSerializable("receive_delivery");
            setInfomation();
        }
    }

    private void setInfomation() {
        mOwnerName.setText(receiveDelivery.getmOwnerName());
        mPackageName.setText(receiveDelivery.getmPackageName());
        mStartLocation.setText(receiveDelivery.getmStartLocation());
        mEndLocation.setText(receiveDelivery.getmEndLocation());
    }

    @OnClick(R.id.auction_auction_btn)
    protected void auction() {

        if (StringUtils.isEmpty(mRateExt.getText().toString().trim())) {
            DialogUtils.showMessageDialog(this, "Nhập Giá Muốn Đặt Của Bạn");
            return;
        }

        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idshipper", String.valueOf(APIClient.getShipperAccount(this).getId()));
        params.put("idpackage", String.valueOf(receiveDelivery.getmIdPackage()));
        params.put("rate", mRateExt.getText().toString().trim());
        params.put("updatetime", dateFormat.format(new Date()));

        APIClient.getInstance().execPost("updateAuctionShipper", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AuctionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AuctionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    AuctionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(AuctionActivity.this, errorResponse.getMessage());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (!errorResponse.hasError()) {
                                        AuctionActivity.this.setResult(Activity.RESULT_OK);
                                    }
                                    AuctionActivity.this.finish();
                                }
                            }, TIME_OUT);
                        }
                    });
                }
            }
        });

    }


}
