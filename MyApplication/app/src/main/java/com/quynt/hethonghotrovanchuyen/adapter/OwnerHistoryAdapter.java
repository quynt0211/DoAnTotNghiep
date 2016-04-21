package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.quynt.hethonghotrovanchuyen.R;

/**
 * PRESENA_Android
 * <p>
 * Created by Paditech on 18/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class OwnerHistoryAdapter extends BaseAdapter {
    private Context mContext;

    public OwnerHistoryAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return "History owner";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_owner_item, null);
        }
        return convertView;
    }
}
