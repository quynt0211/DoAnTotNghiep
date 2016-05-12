package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
 */
public class OwnerHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private OnClickListenner onClickListenner;

    private List<PackageModel> mPackages;

    public void setPackages(List<PackageModel> packages) {
        mPackages = packages;
        notifyDataSetChanged();
    }

    public OwnerHistoryAdapter(Context context) {
        mContext = context;
        mPackages = new ArrayList<PackageModel>();
    }

    public void setOnClickListenner(OnClickListenner onClickListenner) {
        this.onClickListenner = onClickListenner;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final PackageModel packageModel = mPackages.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_owner_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mChangeRequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenner.onChangeRequirement(packageModel);
            }
        });

        viewHolder.mViewShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenner.onViewShipper(packageModel);
            }
        });

        viewHolder.mViewAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenner.onViewAuction(packageModel);
            }
        });

        viewHolder.mDeleteRequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenner.onDeleteRequirement(packageModel.getmIdPackage(), position);
            }
        });


        viewHolder.mPackageName.setText(packageModel.getmPackageName());
        viewHolder.mStartLocation.setText(packageModel.getmStartLocation());
        viewHolder.mEndLocation.setText(packageModel.getmEndLocation());
        viewHolder.mStatus.setText(packageModel.getmStatus());
        viewHolder.mCurrentLocation.setText(packageModel.getmCurrentLocation());
        viewHolder.mUpdateTime.setText(packageModel.getmUpdateTime());
        viewHolder.mShipper.setText(packageModel.getShipperName());
        viewHolder.mPhoneShipper.setText(packageModel.getShipperPhone());

        if (packageModel.isDelivery()) {
            viewHolder.mChangeRequirement.setVisibility(View.GONE);
            viewHolder.mDeleteRequirement.setVisibility(View.GONE);
            viewHolder.mViewAuction.setVisibility(View.GONE);
            viewHolder.mViewShipper.setVisibility(View.GONE);
        } else {
            viewHolder.mChangeRequirement.setVisibility(View.VISIBLE);
            viewHolder.mDeleteRequirement.setVisibility(View.VISIBLE);
            viewHolder.mViewAuction.setVisibility(View.VISIBLE);
            viewHolder.mViewShipper.setVisibility(View.VISIBLE);
        }


        return convertView;
    }


    public static class ViewHolder {
        @Bind(R.id.history_owner_item_package_name)
        TextView mPackageName;
        @Bind(R.id.history_owner_item_start_location)
        TextView mStartLocation;
        @Bind(R.id.history_owner_item_end_location)
        TextView mEndLocation;
        @Bind(R.id.history_owner_item_status)
        TextView mStatus;
        @Bind(R.id.history_owner_item_current_location)
        TextView mCurrentLocation;
        @Bind(R.id.history_owner_item_shipper)
        TextView mShipper;
        @Bind(R.id.history_owner_item_update_time)
        TextView mUpdateTime;

        @Bind(R.id.history_owner_item_phoneshipper)
        TextView mPhoneShipper;

        @Bind(R.id.history_owner_item_change_requirement)
        Button mChangeRequirement;

        @Bind(R.id.history_owner_item_delete_requirement)
        Button mDeleteRequirement;

        @Bind(R.id.history_owner_item_view_shipper)
        Button mViewShipper;

        @Bind(R.id.history_owner_item_view_auction)
        Button mViewAuction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickListenner {
        void onChangeRequirement(PackageModel packageModel);

        void onDeleteRequirement(int idPackage, int position);

        void onViewShipper(PackageModel packageModel);

        void onViewAuction(PackageModel packageModel);
    }
}
