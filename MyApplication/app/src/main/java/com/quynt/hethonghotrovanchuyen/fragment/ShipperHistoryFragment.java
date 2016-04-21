package com.quynt.hethonghotrovanchuyen.fragment;

import android.view.View;
import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.ShipperHistoryAdapter;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 18/04/2016.
 */
public class ShipperHistoryFragment extends BaseFragment {
    @Bind(R.id.history_shipper)
    protected ListView mShipperHistory;

    @Override
    protected int getContentView() {
        return R.layout.fragment_shipper_history;
    }

    @Override
    protected void initView(View view) {
        setupListView();
    }

    private void setupListView() {
        ShipperHistoryAdapter shipperHistoryAdapter = new ShipperHistoryAdapter(getBaseActivity());
        mShipperHistory.setAdapter(shipperHistoryAdapter);
    }
}
