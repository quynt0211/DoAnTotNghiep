package com.quynt.hethonghotrovanchuyen.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.quynt.hethonghotrovanchuyen.R;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 19/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class OwnerHomeAdapter extends BaseAdapter {
    private Context mContext;

    public OwnerHomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return "Owner Home Adapter";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.owner_home_item, null);
        }
        return convertView;
    }
}
