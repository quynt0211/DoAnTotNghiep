package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.response.AccountTypeResponse;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.LoginOwnerResponse;
import com.quynt.hethonghotrovanchuyen.model.response.LoginShipperResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;
import com.quynt.hethonghotrovanchuyen.utils.Const;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.quynt.hethonghotrovanchuyen.utils.StringUtils;
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
 * Created by QuyNT on 12/04/2016.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.login_phone_number)
    protected EditText mPhoneNumberExt;

    @Bind(R.id.login_password)
    protected EditText mPasswordExt;

    private String mPhone;
    private String mPassword;

    private static final int TIME_OUT = 5000;
    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.login_activity_layout_root), this);
        mPhone = "";
        mPassword = "";
    }

    @OnClick(R.id.login_register_owner_button)
    protected void goToRegisterOwner() {
        Intent intent = new Intent(LoginActivity.this, RegisterOwnerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_register_shipper_button)
    protected void goToRegisterShipper() {
        Intent intent = new Intent(LoginActivity.this, RegisterShipperActivity.class);
        startActivity(intent);
    }

    protected void goToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void login() {
        mPhone = mPhoneNumberExt.getText().toString().trim();
        mPassword = mPasswordExt.getText().toString().trim();

        String error = validate();
        if (!StringUtils.isEmpty(error)) {
            DialogUtils.showMessageDialog(this, error);
            return;
        }

        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("phone", mPhone);
        params.put("password", mPassword);

        APIClient.getInstance().execPost("login", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (errorResponse.hasError()) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(LoginActivity.this, errorResponse.getMessage());
                            }
                        });
                    } else {
                        AccountTypeResponse accountTypeResponse = new Gson().fromJson(body, AccountTypeResponse.class);

                        if (accountTypeResponse.getAccountType() == Const.OWNER) {
                            LoginOwnerResponse loginOwnerResponse = new Gson().fromJson(body, LoginOwnerResponse.class);
                            APIClient.saveAccountType(LoginActivity.this, Const.OWNER);
                            APIClient.getInstance().saveLoginAccount(LoginActivity.this, loginOwnerResponse.getOwner());
                            Log.d("accountid", APIClient.getOwnerAccount(LoginActivity.this).getId() + "");
                            Log.d("accountType", APIClient.getAccountType(LoginActivity.this));
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DialogUtils.showMessageDialog(LoginActivity.this, "Đăng Nhập Thành Công");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            goToHome();
                                        }
                                    }, TIME_OUT);
                                }
                            });



                        } else {
                            LoginShipperResponse loginShipperResponse = new Gson().fromJson(body, LoginShipperResponse.class);
                            APIClient.saveAccountType(LoginActivity.this, Const.SHIPPER);
                            APIClient.getInstance().saveLoginAccount(LoginActivity.this, loginShipperResponse.getShipper());
                            Log.d("accountid", APIClient.getShipperAccount(LoginActivity.this).getId() + "");
                            Log.d("accountType", APIClient.getAccountType(LoginActivity.this));
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DialogUtils.showMessageDialog(LoginActivity.this, "Đăng Nhập Thành Công");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            goToHome();
                                        }
                                    }, TIME_OUT);
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    @OnClick(R.id.activity_login_login_button)
    protected void clickLogin(){
        login();
    }


    private String validate() {
        if (StringUtils.isEmpty(mPhone)) {
            return "Vui Lòng Nhập Số Điện Thoại";
        }
        if (StringUtils.isEmpty(mPassword)) {
            return "Vui Lòng Nhập Mật Khẩu";
        }
        return "";
    }
}
