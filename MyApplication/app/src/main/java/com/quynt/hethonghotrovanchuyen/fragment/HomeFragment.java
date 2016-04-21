package com.quynt.hethonghotrovanchuyen.fragment;

import android.view.View;
import android.widget.ListView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.HomeAdapter;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 15/04/2016.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.home_listview)
    ListView mPostList;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        setupList();
    }

    private void setupList() {
        HomeAdapter homeAdapter = new HomeAdapter(getBaseActivity());
        mPostList.setAdapter(homeAdapter);
    }

}
