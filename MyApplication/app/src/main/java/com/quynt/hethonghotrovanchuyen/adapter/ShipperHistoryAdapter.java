package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.quynt.hethonghotrovanchuyen.R;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by Paditech on 19/03/2016.
 */
public class ShipperHistoryAdapter extends BaseAdapter {
    private Context mContext;

    public ShipperHistoryAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return "History Shipper";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_shipper_item, null);
        }
        return convertView;
    }

    static class ViewHolder {
    }
}
