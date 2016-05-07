package com.quynt.hethonghotrovanchuyen.fragment;

import android.app.Dialog;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.activity.BaseActivity;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.OwnerHomeResponse;
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
 * PRESENA_Android
 * <p>
 * Created by Paditech on 19/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class OwnerHomeFragment extends BaseFragment {
    @Bind(R.id.owner_home_listview)
    protected ListView mOwnerHome;

    OwnerHomeAdapter ownerHomeAdapter;

    private BaseActivity mBaseActivity;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_owner;
    }

    @Override
    protected void initView(View view) {
        setupListView();
        getDeliveryRequirements();
    }

    private void setupListView() {
        ownerHomeAdapter = new OwnerHomeAdapter(getBaseActivity());
        mOwnerHome.setAdapter(ownerHomeAdapter);
    }

    private void getDeliveryRequirements() {
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();

        APIClient.getInstance().execPost("getDeliveryRequirements", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        final OwnerHomeResponse ownerHomeResponse = new Gson().fromJson(body, OwnerHomeResponse.class);
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ownerHomeAdapter.setPackages(ownerHomeResponse.getPackage());
                            }
                        });
                    } else {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }
}
