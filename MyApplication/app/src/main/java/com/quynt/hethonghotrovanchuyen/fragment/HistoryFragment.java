package com.quynt.hethonghotrovanchuyen.fragment;

import android.view.View;
import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.OwnerHistoryAdapter;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 15/03/2016.
 */
public class HistoryFragment extends BaseFragment {
    @Bind(R.id.history_owner)
    ListView mOwnerHistorys;

    @Override
    protected int getContentView() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View view) {
        initListView();
    }

    private void initListView() {
        OwnerHistoryAdapter ownerHistoryAdapter = new OwnerHistoryAdapter(getBaseActivity());
        mOwnerHistorys.setAdapter(ownerHistoryAdapter);
    }
}
