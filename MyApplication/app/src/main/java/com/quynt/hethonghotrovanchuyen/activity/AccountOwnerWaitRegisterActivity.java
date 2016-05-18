package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.widget.ListView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AccountOwnerWaitRegisterAdapter;
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
public class AccountOwnerWaitRegisterActivity extends BaseActivity implements AccountOwnerWaitRegisterAdapter.OnButtonClickListenner {
    @Bind(R.id.account_owner_wait_register_list)
    ListView mWaitRegisterList;

    private AccountOwnerWaitRegisterAdapter accountOwnerWaitRegisterAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_account_owner_wait_register;
    }

    @Override
    protected void initView() {
        initListView();
        getOwnerAccount();
    }

    private void initListView() {
        accountOwnerWaitRegisterAdapter = new AccountOwnerWaitRegisterAdapter(this);
        accountOwnerWaitRegisterAdapter.setOnButtonClickListenner(this);
        mWaitRegisterList.setAdapter(accountOwnerWaitRegisterAdapter);
    }

    @Override
    public void onAllowClick(Owner owner) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idowner", String.valueOf(owner.getId()));

        APIClient.getInstance().execPost("allowRegister", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(AccountOwnerWaitRegisterActivity.this, errorResponse.getMessage());
                        }
                    });
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
                AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(AccountOwnerWaitRegisterActivity.this, errorResponse.getMessage());
                        }
                    });
                }
            }
        });
    }


    private void getOwnerAccount() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("type", String.valueOf(0));

        APIClient.getInstance().execPost("getOwners", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
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
                        AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accountOwnerWaitRegisterAdapter.setOwners(ownerAccountInSystemResponse.getOwners());
                            }
                        });
                    } else {
                        AccountOwnerWaitRegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(AccountOwnerWaitRegisterActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });

    }
}
