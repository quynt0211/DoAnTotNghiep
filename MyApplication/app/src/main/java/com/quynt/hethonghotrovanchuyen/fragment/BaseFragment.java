package com.quynt.hethonghotrovanchuyen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/04/2016.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    protected abstract int getContentView();

    protected abstract void initView(View view);


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBaseActivity = (BaseActivity) getActivity();
        final View view = getView();
        ButterKnife.bind(this, view);
        initView(view);
    }



    public String getTagText() {
        return this.getClass().getSimpleName();
    }


    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }
}
