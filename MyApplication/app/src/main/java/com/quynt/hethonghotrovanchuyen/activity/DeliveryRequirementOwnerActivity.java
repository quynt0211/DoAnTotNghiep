package com.quynt.hethonghotrovanchuyen.activity;

import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.DeliveryRequirementOwnerAdapter;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
 */
public class DeliveryRequirementOwnerActivity extends BaseActivity {
    @Bind(R.id.delivery_requirement_owner_list_view)
    protected ListView mDeliveryRequirementList;


    @Override
    protected int getContentView() {
        return R.layout.activity_delivery_requirement_owner;
    }

    @Override
    protected void initView() {
        initListView();
    }

    private void initListView() {
        DeliveryRequirementOwnerAdapter deliveryRequirementOwnerAdapter = new DeliveryRequirementOwnerAdapter(this);
        mDeliveryRequirementList.setAdapter(deliveryRequirementOwnerAdapter);
    }


}
