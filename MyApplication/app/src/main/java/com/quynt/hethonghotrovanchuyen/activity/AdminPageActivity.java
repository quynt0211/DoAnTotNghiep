package com.quynt.hethonghotrovanchuyen.activity;

import android.content.Intent;

import com.quynt.hethonghotrovanchuyen.R;

import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 14/05/2016.
 */
public class AdminPageActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_admin_page;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.admin_page_owner)
    protected void mangageOwner() {
        Intent intent = new Intent(this, AdminPageHomeOwnerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.admin_page_shipper)
    protected void manageShipper() {
        Intent intent = new Intent(this, AdminPageHomeShipperActivity.class);
        startActivity(intent);
    }
}
