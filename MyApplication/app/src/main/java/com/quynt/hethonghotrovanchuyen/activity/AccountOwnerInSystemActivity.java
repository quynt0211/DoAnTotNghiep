package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.widget.ListView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AccounOwnerInSystemAdapter;
import com.quynt.hethonghotrovanchuyen.model.Owner;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.OwnerAccountInSystemResponse;
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
 * Created by QuyNT on 14/05/2016.
 */
public class AccountOwnerInSystemActivity extends BaseActivity implements AccounOwnerInSystemAdapter.OnButtonClickListenner {
    @Bind(R.id.account_owner_in_system_list)
    protected ListView mListAccount;
    private AccounOwnerInSystemAdapter accounOwnerInSystemAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_account_owner_in_system;
    }
    // this is the comment master
    // this is the comment master
    // this is the comment master

    @Override
    protected void initView() {
        initListView();
        getOwnerAccount();
    }

    private void initListView() {
        accounOwnerInSystemAdapter = new AccounOwnerInSystemAdapter(this);
        accounOwnerInSystemAdapter.setOnButtonClickListenner(this);
        mListAccount.setAdapter(accounOwnerInSystemAdapter);
    }

    private void getOwnerAccount() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("type", String.valueOf(2));

        APIClient.getInstance().execPost("getOwners", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AccountOwnerInSystemActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountOwnerInSystemActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        final OwnerAccountInSystemResponse ownerAccountInSystemResponse = new Gson().fromJson(body, OwnerAccountInSystemResponse.class);
                        AccountOwnerInSystemActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accounOwnerInSystemAdapter.setOwners(ownerAccountInSystemResponse.getOwners());
                            }
                        });
                    } else {
                        AccountOwnerInSystemActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(AccountOwnerInSystemActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });

    }


    @Override
    public void onBlockClick(Owner owner) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idowner", String.valueOf(owner.getId()));

        APIClient.getInstance().execPost("blockOwner", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AccountOwnerInSystemActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountOwnerInSystemActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    AccountOwnerInSystemActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(AccountOwnerInSystemActivity.this, errorResponse.getMessage());
                        }
                    });
                }
            }
        });
    }
}
