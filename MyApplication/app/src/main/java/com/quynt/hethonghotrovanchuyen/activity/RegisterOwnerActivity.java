package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.RegisterOwnerResponse;
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
 * Created by QuyNT on 13/04/2016.
 */
public class RegisterOwnerActivity extends BaseActivity {

    private String mName;
    private String mPhone;
    private String mAddress;
    private String mPassword;
    private String mConfirmPassword;

    @Bind(R.id.register_owner_name)
    protected EditText mNameExt;

    @Bind(R.id.register_owner_phone)
    protected EditText mPhoneExt;

    @Bind(R.id.register_owner_address)
    protected EditText mAddressExt;

    @Bind(R.id.register_owner_password)
    protected EditText mPasswordExt;

    @Bind(R.id.register_owner_confirm_password)
    protected EditText mConfirmPasswordExt;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_owner;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.register_owner_activity_layout_root), this);
    }

    @OnClick(R.id.register_owner_register)
    protected void registerOwner() {
        register();
    }

    @OnClick(R.id.register_owner_cancel)
    protected void cancelRegistion() {
        this.finish();
    }

    private void register() {

        mName = mNameExt.getText().toString().trim();
        mPhone = mPhoneExt.getText().toString().trim();
        mAddress = mAddressExt.getText().toString().trim();
        mPassword = mPasswordExt.getText().toString().trim();
        Log.d("mPassword", mPassword);
        mConfirmPassword = mConfirmPasswordExt.getText().toString().trim();
        Log.d("mPasswordConfirm", mConfirmPassword);

        final String error = validate();

        if (!StringUtils.isEmpty(error)) {
            DialogUtils.showMessageDialog(this, error);
            return;
        }
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("name", mName);
        params.put("phone", mPhone);
        Log.d("phone", mPhone);

        params.put("address", mAddress);
        params.put("password", mPassword);

        APIClient.getInstance().execPost("createOwner", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                RegisterOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                RegisterOwnerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                String body = response.body().string();
                final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);

                if (!errorResponse.hasError()) {
                    RegisterOwnerResponse registerOwnerResponse = new Gson().fromJson(body, RegisterOwnerResponse.class);
                    APIClient.getInstance().saveAccountType(RegisterOwnerActivity.this, Const.OWNER);
                    APIClient.getInstance().saveLoginAccount(RegisterOwnerActivity.this, registerOwnerResponse.getOwner());

                    goToHome();
                } else {
                    RegisterOwnerActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(RegisterOwnerActivity.this, errorResponse.getMessage());
                        }
                    });
                }
            }
        });
    }

    protected void goToHome() {
        Intent intent = new Intent(RegisterOwnerActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("accountType", Const.OWNER);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private String validate() {
        if (StringUtils.isEmpty(mName)) {
            return "Vui Lòng Nhập Tên";
        }
        if (StringUtils.isEmpty(mAddress)) {
            return "Vui Lòng Nhập Địa Chỉ";
        }

        if (StringUtils.isEmpty(mPhone)) {
            return "Vui Lòng Nhập Số Điện Thoại";
        }

        if (StringUtils.isEmpty(mPassword)) {
            return "Vui Lòng Nhập Mật Khẩu";
        }

        if (StringUtils.isEmpty(mPassword)) {
            return "Vui Lòng Xác Nhận Mật Khẩu";
        }

        if (StringUtils.isEmpty(mConfirmPassword)) {
            return "Vui Lòng Xác Nhận Mật Khẩu";
        }

        if (!mPassword.equals(mConfirmPassword)) {
            return "Kiểm Tra Lại Mật Khẩu";
        }
        return "";
    }
}
