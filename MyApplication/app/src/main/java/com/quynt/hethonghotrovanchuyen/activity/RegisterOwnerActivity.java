package com.quynt.hethonghotrovanchuyen.activity;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 13/04/2016.
 */
public class RegisterOwnerActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_register_owner;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.register_owner_activity_layout_root), this);
    }
}
