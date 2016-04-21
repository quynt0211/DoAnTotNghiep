package com.quynt.hethonghotrovanchuyen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.fragment.HistoryFragment;
import com.quynt.hethonghotrovanchuyen.fragment.HomeFragment;
import com.quynt.hethonghotrovanchuyen.fragment.MyPageFragment;
import com.quynt.hethonghotrovanchuyen.fragment.OtherFragment;
import com.quynt.hethonghotrovanchuyen.fragment.OwnerHomeFragment;
import com.quynt.hethonghotrovanchuyen.fragment.ShipperHistoryFragment;
import com.quynt.hethonghotrovanchuyen.fragment.ShipperOtherFragment;
import com.quynt.hethonghotrovanchuyen.fragment.ShipperPagerFragment;
import com.quynt.hethonghotrovanchuyen.utils.Const;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 13/03/2016.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    Menu mCurrentMenu;
    private int user;
    @Bind(R.id.tab_host)
    FragmentTabHost mTabHost;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();

        user = bundle.getInt("user");
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        if (user == Const.OWNER) {
            mTabHost.addTab(mTabHost.newTabSpec(Menu.HISTORY.getCode()).setIndicator(Menu.HISTORY.name()), HistoryFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec(Menu.HOME.getCode()).setIndicator(Menu.HOME.name()), OwnerHomeFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec(Menu.MYPAGE.getCode()).setIndicator(Menu.MYPAGE.name()), MyPageFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec(Menu.OTHER.getCode()).setIndicator(Menu.OTHER.name()), OtherFragment.class, null);
        } else if (user == Const.SHIPPER) {
            mTabHost.addTab(mTabHost.newTabSpec(Menu.HISTORY.getCode()).setIndicator(Menu.HISTORY.name()), ShipperHistoryFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec(Menu.HOME.getCode()).setIndicator(Menu.HOME.name()), HomeFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec(Menu.MYPAGE.getCode()).setIndicator(Menu.MYPAGE.name()), ShipperPagerFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec(Menu.OTHER.getCode()).setIndicator(Menu.OTHER.name()), ShipperOtherFragment.class, null);
        }

        mTabHost.getTabWidget().setVisibility(View.GONE);
        for (Menu menu : Menu.values()) {
            findViewById(menu.getIdButton()).setOnClickListener(this);
        }
        changeMenu(Menu.HOME);
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if (Menu.HOME.getIdButton() == idView) {
            changeMenu(Menu.HOME);
        } else if (Menu.HISTORY.getIdButton() == idView) {
            changeMenu(Menu.HISTORY);
        } else if (Menu.MYPAGE.getIdButton() == idView) {
            changeMenu(Menu.MYPAGE);
        } else if (Menu.OTHER.getIdButton() == idView) {
            changeMenu(Menu.OTHER);
        }
    }

    private void changeMenu(Menu menu) {
        mTabHost.setCurrentTabByTag(menu.getCode());
        if (mCurrentMenu != null) {
            findViewById(mCurrentMenu.getIdButton()).setBackgroundColor(getResources().getColor(R.color.gray));
            ((TextView) findViewById(mCurrentMenu.getIdButton())).setTextColor(getResources().getColor(R.color.dark_gray));
        }
        findViewById(menu.getIdButton()).setBackgroundColor(getResources().getColor(R.color.back_ground));
        ((TextView) findViewById(menu.getIdButton())).setTextColor(getResources().getColor(R.color.white));
        mCurrentMenu = menu;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("home_act", "OnDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("home_act", "OnREsume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("home_act", "OnPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("home_act", "OnSStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("home_act", "OnStop");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("home_act", "OnBackPRessed");
    }

    public enum Menu {
        HOME("home", R.id.home_home_button),
        HISTORY("history", R.id.home_history_button),
        MYPAGE("mypage", R.id.home_my_page_button),
        OTHER("other", R.id.home_other_button);


        private String code;
        private int idButton;

        Menu(String code, int idButton) {
            this.code = code;
            this.idButton = idButton;
        }
        public String getCode() {
            return code;
        }

        public int getIdButton() {
            return idButton;
        }
    }


}
