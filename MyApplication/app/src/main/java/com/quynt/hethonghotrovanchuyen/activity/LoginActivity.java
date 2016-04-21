package com.quynt.hethonghotrovanchuyen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/04/2016.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.login_phone_number)
    protected EditText mPhoneNumber;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.login_activity_layout_root), this);
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

    @OnClick(R.id.activity_login_login_button)
    protected void goToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("user", Integer.parseInt(mPhoneNumber.getText().toString()));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
