package com.quynt.hethonghotrovanchuyen.fragment;

import android.view.View;
import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;

import butterknife.Bind;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 19/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class OwnerHomeFragment extends BaseFragment {
    @Bind(R.id.owner_home_listview)
    protected ListView mOwnerHome;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_owner;
    }

    @Override
    protected void initView(View view) {
        setupListView();
    }

    private void setupListView() {
        OwnerHomeAdapter ownerHomeAdapter = new OwnerHomeAdapter(getBaseActivity());
        mOwnerHome.setAdapter(ownerHomeAdapter);
    }
}
