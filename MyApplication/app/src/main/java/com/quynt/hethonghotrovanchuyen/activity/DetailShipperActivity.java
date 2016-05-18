package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Auction;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.Shipper;
import com.quynt.hethonghotrovanchuyen.model.response.DetailAuctionResponse;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.RegisterShipperResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/05/2016.
 */
public class DetailShipperActivity extends BaseActivity {

    @Bind(R.id.detail_shipper_package_name)
    TextView mPackageName;

    @Bind(R.id.detail_shipper_shipper_name)
    TextView mShipperName;

    @Bind(R.id.detail_shipper_phone)
    TextView mShipperPhone;

    @Bind(R.id.detail_shipper_address)
    TextView mShipperAddress;

    @Bind(R.id.detail_shipper_can_delivery)
    TextView mShipperCanDelivery;

    @Bind(R.id.detail_shipper_introduce)
    TextView mShipperIntroduce;

    @Bind(R.id.detail_shipper_package_rate)
    TextView mRate;


    PackageModel packageModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_detail_shipper;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            packageModel = (PackageModel) bundle.getSerializable("package_model");
            mPackageName.setText(packageModel.getmPackageName());
            getInfomationShipper();
            getRate();
        }
    }

    private void getInfomationShipper() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idshipper", String.valueOf(packageModel.getmIdShipper()));

        APIClient.getInstance().execPost("getDetailShipper", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DetailShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DetailShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d("detail_shipper", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (errorResponse.hasError()) {
                        DetailShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(DetailShipperActivity.this, errorResponse.getMessage());
                            }
                        });
                    } else {
                        RegisterShipperResponse registerShipperResponse = new Gson().fromJson(body, RegisterShipperResponse.class);
                        final Shipper shiper = registerShipperResponse.getShipper();
                        DetailShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setInfoShipper(shiper);
                            }
                        });
                    }
                }
            }
        });
    }

    private void getRate() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idshipper", String.valueOf(packageModel.getmIdShipper()));
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));

        APIClient.getInstance().execPost("getRate", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DetailShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DetailShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        DetailAuctionResponse detailAuctionResponse = new Gson().fromJson(body, DetailAuctionResponse.class);
                        final Auction auction = detailAuctionResponse.getAuction();
                        DetailShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRate.setText(String.valueOf(auction.getmRate()));
                            }
                        });
                    }
                }
            }
        });
    }

    private void setInfoShipper(Shipper shipper) {
        mShipperName.setText(shipper.getName());
        mShipperPhone.setText(shipper.getPhoneNumber());
        mShipperAddress.setText(shipper.getmAddress());
        mShipperIntroduce.setText(shipper.getmIntroduce());
        mShipperCanDelivery.setText(convertFeture(shipper));
    }

    private StringBuilder convertFeture(Shipper shipper) {
        StringBuilder feture = new StringBuilder();
        if (shipper.isSamples()) {
            feture.append("Hàng Mẫu Vật ");
        }
        if (shipper.isBulky()) {
            feture.append("Hàng Cồng Kềnh ");
        }
        if (shipper.isInflammable()) {
            feture.append("Hàng Dễ Cháy ");
        }
        if (shipper.isFragile()) {
            feture.append("Hàng Dễ Vỡ ");
        }
        if (shipper.isHeavy()) {
            feture.append("Hàng Nặng");
        }
        return feture;
    }

    @OnClick(R.id.detail_shipper_ok)
    protected void clickOk() {
        this.finish();
    }


}
