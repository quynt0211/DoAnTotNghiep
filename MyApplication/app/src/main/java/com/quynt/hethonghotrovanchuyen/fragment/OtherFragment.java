package com.quynt.hethonghotrovanchuyen.fragment;

import android.content.Intent;
import android.view.View;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.activity.CreateDeliveryRequirementActivity;

import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 15/03/2016.
 */
public class OtherFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView(View view) {

    }

    @OnClick(R.id.create_delivery_requirement)
    protected void goToCreateDeliveryRequiements() {
        Intent intent = new Intent(getBaseActivity(), CreateDeliveryRequirementActivity.class);
        startActivity(intent);
    }

}
