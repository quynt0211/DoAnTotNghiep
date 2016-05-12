package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AuctionOwnerAdapter;
import com.quynt.hethonghotrovanchuyen.model.Auction;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.ViewAuctionOwnerAllowedResponse;
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
public class AuctionOwnerActivity extends BaseActivity implements AuctionOwnerAdapter.OnClickAllowDelivery {
    @Bind(R.id.auction_owner_listview)
    protected ListView mAuctionOwners;

    @Bind(R.id.auction_owner_package_name)
    protected TextView mPackageName;

    @Bind(R.id.auction_owner_start_location)
    protected TextView mStartLocation;

    @Bind(R.id.auction_owner_empty)
    TextView mEmptyView;

    @Bind(R.id.auction_owner_end_location)
    protected TextView mEndLocation;
    AuctionOwnerAdapter auctionOwnerAdapter;

    private PackageModel packageModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_auction_owner;
    }

    @Override
    protected void initView() {
        initListView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            packageModel = (PackageModel) bundle.getSerializable("package_model");
            setInformation();
        }

        getAuctionAllowed();
    }

    private void initListView() {
        auctionOwnerAdapter = new AuctionOwnerAdapter(this);
        auctionOwnerAdapter.setOnClickAllowDelivery(this);
        mAuctionOwners.setAdapter(auctionOwnerAdapter);
    }


    private void setInformation() {
        mPackageName.setText(packageModel.getmPackageName());
        mEndLocation.setText(packageModel.getmEndLocation());
        mStartLocation.setText(packageModel.getmStartLocation());
    }

    private void getAuctionAllowed() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idowner", String.valueOf(APIClient.getOwnerAccount(this).getId()));

        APIClient.getInstance().execPost("viewAuctionAllowed", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AuctionOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AuctionOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (errorResponse.hasError()) {
                        DialogUtils.showMessageDialog(AuctionOwnerActivity.this, errorResponse.getMessage());
                        return;
                    } else {
                        final ViewAuctionOwnerAllowedResponse viewAuctionOwnerAllowedResponse = new Gson().fromJson(body, ViewAuctionOwnerAllowedResponse.class);
                        AuctionOwnerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int size = viewAuctionOwnerAllowedResponse.getAuctions().size();
                                if (size == 0) {
                                    mEmptyView.setVisibility(View.VISIBLE);
                                    return;
                                } else {
                                    mEmptyView.setVisibility(View.GONE);
                                }
                                auctionOwnerAdapter.setAuctions(viewAuctionOwnerAllowedResponse.getAuctions());
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onClickAllow(Auction auction) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idowner", String.valueOf(APIClient.getOwnerAccount(this).getId()));
        params.put("idshipper", String.valueOf(auction.getId()));
        params.put("nameshipper", auction.getmName());
        params.put("phoneshipper", auction.getmPhoneNumber());

        APIClient.getInstance().execPost("allowDelivery", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AuctionOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AuctionOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d("allow_delivery", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (errorResponse.hasError()) {
                        AuctionOwnerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(AuctionOwnerActivity.this, errorResponse.getMessage());
                            }
                        });
                        return;
                    }

                    AuctionOwnerActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAuctionAllowed();
                            DialogUtils.showMessageDialog(AuctionOwnerActivity.this, errorResponse.getMessage());
                        }
                    });


                }
            }
        });
    }
}
