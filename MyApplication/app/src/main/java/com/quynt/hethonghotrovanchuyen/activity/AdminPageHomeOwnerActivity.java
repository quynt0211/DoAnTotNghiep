package com.quynt.hethonghotrovanchuyen.activity;

import android.content.Intent;

import com.quynt.hethonghotrovanchuyen.R;

import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 14/05/2016.
 */
public class AdminPageHomeOwnerActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_admin_page_home_owner;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.admin_page_home_owner_wait_register)
    protected void goToAccountOwnerWaitRegister() {
        Intent intent = new Intent(this, AccountOwnerWaitRegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.admin_page_home_owner_account_in_system)
    protected void goToAccountOwnerInSystem() {
        Intent intent = new Intent(this, AccountOwnerInSystemActivity.class);
        startActivity(intent);
    }
}
