package com.quynt.hethonghotrovanchuyen.activity;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/04/2016.
 */
public class RegisterShipperActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_register_shipper;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.register_shipper_activity_layout_root), this);
    }
}
