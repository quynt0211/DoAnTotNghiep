package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.quynt.hethonghotrovanchuyen.R;

/**
 * PRESENA_Android
 * <p/>
 * Created by Paditech on 20/04/2016.
 * Copyright (c) 2015 Paditech. All rights reserved.
 */
public class DeliveryShipperAdapter extends BaseAdapter {
    private Context mContext;

    public DeliveryShipperAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return "auction shipper";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.delivery_shipper_item, null);
        }
        return convertView;

    }
}
