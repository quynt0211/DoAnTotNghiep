package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.DeliveryRequirementOwnerAdapter;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.Shipper;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.ViewAuctionOwnerResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
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
public class DeliveryRequirementOwnerActivity extends BaseActivity implements DeliveryRequirementOwnerAdapter.OnClickAllowAuction {
    @Bind(R.id.delivery_requirement_owner_list_view)
    protected ListView mDeliveryRequirementList;

    @Bind(R.id.delivery_requirement_owner_package_name)
    protected TextView mPackageName;

    @Bind(R.id.delivery_requirement_owner_start_location)
    protected TextView mStartLocation;

    @Bind(R.id.delivery_requirement_owner_end_location)
    protected TextView mEndLocation;

    private PackageModel packageModel;
    DeliveryRequirementOwnerAdapter deliveryRequirementOwnerAdapter;

    @Bind(R.id.delivery_requirement_owner_empty)
    TextView mEmptyView;

    private static final int TIME_OUT = 2000;

    @Override
    protected int getContentView() {
        return R.layout.activity_delivery_requirement_owner;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            packageModel = (PackageModel) bundle.getSerializable("package_model");
        }

        initListView();
        setInfomation();
        getShipperAuction();
    }

    private void initListView() {
        deliveryRequirementOwnerAdapter = new DeliveryRequirementOwnerAdapter(this);
        deliveryRequirementOwnerAdapter.setOnClickAllowAuction(this);
        mDeliveryRequirementList.setAdapter(deliveryRequirementOwnerAdapter);
    }

    private void setInfomation() {
        mPackageName.setText(packageModel.getmPackageName());
        mEndLocation.setText(packageModel.getmEndLocation());
        mStartLocation.setText(packageModel.getmStartLocation());
    }

    private void getShipperAuction() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idowner", String.valueOf(APIClient.getOwnerAccount(this).getId()));

        APIClient.getInstance().execPost("viewAuction", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DeliveryRequirementOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DeliveryRequirementOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (errorResponse.hasError()) {
                        DialogUtils.showMessageDialog(DeliveryRequirementOwnerActivity.this, errorResponse.getMessage());
                        return;
                    } else {
                        final ViewAuctionOwnerResponse viewAuctionOwnerResponse = new Gson().fromJson(body, ViewAuctionOwnerResponse.class);

                        DeliveryRequirementOwnerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int size = viewAuctionOwnerResponse.getShipper().size();
                                if (size == 0) {
                                    mEmptyView.setVisibility(View.VISIBLE);
                                } else {
                                    mEmptyView.setVisibility(View.GONE);
                                }
                                deliveryRequirementOwnerAdapter.setShippers(viewAuctionOwnerResponse.getShipper());
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onClickAllow(Shipper shipper) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idowner", String.valueOf(APIClient.getOwnerAccount(this).getId()));
        params.put("idshipper", String.valueOf(shipper.getId()));

        APIClient.getInstance().execPost("allowAuction", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DeliveryRequirementOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DeliveryRequirementOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d("body_click_allow", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        DeliveryRequirementOwnerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getShipperAuction();
                                    }
                                }, TIME_OUT);

                                DialogUtils.showMessageDialog(DeliveryRequirementOwnerActivity.this, errorResponse.getMessage());
                            }
                        });
                    } else {
                        DeliveryRequirementOwnerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(DeliveryRequirementOwnerActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }
}
