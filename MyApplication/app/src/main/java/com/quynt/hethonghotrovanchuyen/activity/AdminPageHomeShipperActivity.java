package com.quynt.hethonghotrovanchuyen.activity;

import android.content.Intent;

import com.quynt.hethonghotrovanchuyen.R;

import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 14/05/2016.
 */
public class AdminPageHomeShipperActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_admin_page_home_shipper;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.admin_page_home_shipper_wait_register)
    protected void goToShipperWaitRegister(){
        Intent intent = new Intent(this, AccountShipperWaitRegister.class);
        startActivity(intent);
    }

    @OnClick(R.id.admin_page_home_shipper_in_system)
    protected void goToShipperInSystem(){
        Intent intent = new Intent(this, AccountShipperInSystemActivity.class);
        startActivity(intent);
    }
}
