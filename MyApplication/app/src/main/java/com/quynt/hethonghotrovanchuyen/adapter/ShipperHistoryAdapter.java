package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by Paditech on 19/03/2016.
 */
public class ShipperHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<PackageModel> mPackages;
    private OnClickButtonListenner onClickButtonListenner;


    public void setOnClickButtonListenner(OnClickButtonListenner onClickButtonListenner) {
        this.onClickButtonListenner = onClickButtonListenner;
    }

    public ShipperHistoryAdapter(Context context) {
        this.mContext = context;
        mPackages = new ArrayList<PackageModel>();
    }

    public void setPackages(List<PackageModel> packages) {
        this.mPackages = packages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPackages.size();
    }

    @Override
    public PackageModel getItem(int position) {
        return mPackages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PackageModel packageModel = mPackages.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_shipper_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mPackageName.setText(packageModel.getmPackageName());
        viewHolder.mCurrentLocationExt.setText(packageModel.getmCurrentLocation());
        viewHolder.mEndLocation.setText(packageModel.getmEndLocation());
        viewHolder.mOwnerName.setText(packageModel.getmOwnerName());
        viewHolder.mStatusExt.setText(packageModel.getmStatus());
        viewHolder.mUpdateTime.setText(packageModel.getmUpdateTime());
        viewHolder.mStartLocation.setText(packageModel.getmStartLocation());
        viewHolder.mOwnerPhone.setText(packageModel.getOwnerPhone());
        viewHolder.mUpdateLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonListenner.clickUpdateLocation(packageModel, viewHolder.mCurrentLocationExt.getText().toString().trim());
            }
        });

        viewHolder.mUpdateStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonListenner.clickUpdateStatus(packageModel, viewHolder.mStatusExt.getText().toString().trim());
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.shipper_history_package_name)
        TextView mPackageName;

        @Bind(R.id.shipper_history_owner_name)
        TextView mOwnerName;

        @Bind(R.id.shipper_history_start_location)
        TextView mStartLocation;

        @Bind(R.id.shipper_history_end_location)
        TextView mEndLocation;

        @Bind(R.id.shipper_history_status)
        EditText mStatusExt;

        @Bind(R.id.shipper_history_current_location)
        EditText mCurrentLocationExt;

        @Bind(R.id.shipper_history_item_update_status)
        Button mUpdateStatusBtn;

        @Bind(R.id.shipper_history_item_update_location)
        Button mUpdateLocationBtn;

        @Bind(R.id.shipper_history_update_time)
        TextView mUpdateTime;

        @Bind(R.id.shipper_history_owner_phone)
        TextView mOwnerPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface OnClickButtonListenner {
        void clickUpdateLocation(PackageModel packageModel, String location);

        void clickUpdateStatus(PackageModel packageModel, String status);
    }
}
