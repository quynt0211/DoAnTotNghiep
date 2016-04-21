package com.quynt.hethonghotrovanchuyen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.quynt.hethonghotrovanchuyen.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initView();
    }

    protected abstract int getContentView();

    protected abstract void initView();

    public void showFragment(BaseFragment fragment, int container, boolean isStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(container, fragment);
        if (isStack) {
            transaction.addToBackStack(fragment.getTagText());
        }
        transaction.commit();
    }

    public void backToPreviousFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}
