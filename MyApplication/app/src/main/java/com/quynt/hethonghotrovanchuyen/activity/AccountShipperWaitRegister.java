package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.AccountShipperWaitRegisterAdapter;
import com.quynt.hethonghotrovanchuyen.model.Shipper;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.GetShippersResponse;
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
 * Created by QuyNT on 18/05/2016.
 */
public class AccountShipperWaitRegister extends BaseActivity implements AccountShipperWaitRegisterAdapter.OnButtonClickListenner {
    @Bind(R.id.account_shipper_wait_register_list)
    ListView mShipperWaitRegister;
    AccountShipperWaitRegisterAdapter accountShipperWaitRegisterAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_account_shipper_wait_register;
    }

    @Override
    protected void initView() {
        initListView();
        getShipper();
    }

    private void initListView() {
        accountShipperWaitRegisterAdapter = new AccountShipperWaitRegisterAdapter(this);
        accountShipperWaitRegisterAdapter.setOnButtonClickListenner(this);
        mShipperWaitRegister.setAdapter(accountShipperWaitRegisterAdapter);
    }

    @Override
    public void onBlockAccount(Shipper shipper) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idshipper", String.valueOf(shipper.getId()));

        APIClient.getInstance().execPost("blockShipper", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d("body_bl__in_system", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(AccountShipperWaitRegister.this, errorResponse.getMessage());
                        }
                    });
                    if (!errorResponse.hasError()) {
                        AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getShipper();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onAllowRegister(Shipper shipper) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idshipper", String.valueOf(shipper.getId()));

        APIClient.getInstance().execPost("allowShipperRegister", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d("body_allow_wait_r", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(AccountShipperWaitRegister.this, errorResponse.getMessage());
                        }
                    });
                    if(!errorResponse.hasError()){
                        AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getShipper();
                            }
                        });
                    }
                }
            }
        });
    }

    private void getShipper() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("type", String.valueOf(0));

        APIClient.getInstance().execPost("getShippers", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d("body_shipper_in_system", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        final GetShippersResponse getShippersResponse = new Gson().fromJson(body, GetShippersResponse.class);
                        AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accountShipperWaitRegisterAdapter.setShippers(getShippersResponse.getShipper());
                            }
                        });

                    } else {
                        AccountShipperWaitRegister.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(AccountShipperWaitRegister.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }
}
