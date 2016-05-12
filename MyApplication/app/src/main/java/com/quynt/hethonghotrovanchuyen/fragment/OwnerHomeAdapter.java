package com.quynt.hethonghotrovanchuyen.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/* He Thong Ho Tro Van Chuyen
* <p/>
* Created by QuyNT on 12/03/2016.
*/
public class OwnerHomeAdapter extends BaseAdapter {
    private Context mContext;

    private List<PackageModel> mPackages;

    public OwnerHomeAdapter(Context mContext) {
        this.mContext = mContext;
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

    private StringBuilder convertFeture(PackageModel mPackage) {
        StringBuilder feture = new StringBuilder();
        if (mPackage.isSample()) {
            feture.append("Hàng Mẫu Vật ");
        }
        if (mPackage.isBulky()) {
            feture.append("Hàng Cồng Kềnh ");
        }
        if (mPackage.isFlammable()) {
            feture.append("Hàng Dễ Cháy ");
        }
        if (mPackage.isFragile()) {
            feture.append("Hàng Dễ Vỡ ");
        }
        if (mPackage.isHeavy()) {
            feture.append("Hàng Nặng");
        }
        return feture;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.owner_home_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mFeture.setText(convertFeture(mPackages.get(position)));
        viewHolder.mOwnerName.setText(mPackages.get(position).getmOwnerName());
        viewHolder.mDescrition.setText(mPackages.get(position).getmDescription());
        viewHolder.mEndLocation.setText(mPackages.get(position).getmEndLocation());
        viewHolder.mPackageName.setText(mPackages.get(position).getmPackageName());
    //    viewHolder.mStatus.setText(mPackages.get(position).getmStatus());
        viewHolder.mStartLocation.setText(mPackages.get(position).getmStartLocation());
        viewHolder.mCreateTime.setText(mPackages.get(position).getmCreateTime());
        viewHolder.mOwnerPhone.setText(mPackages.get(position).getOwnerPhone());
        return convertView;
    }


    public static class ViewHolder {
        @Bind(R.id.owner_home_item_owner_name)
        TextView mOwnerName;

//        @Bind(R.id.owner_home_item_status)
//        TextView mStatus;

        @Bind(R.id.owner_home_item_package_name)
        TextView mPackageName;

        @Bind(R.id.owner_home_item_start_location)
        TextView mStartLocation;

        @Bind(R.id.owner_home_item_end_location)
        TextView mEndLocation;

        @Bind(R.id.owner_home_item_feture)
        TextView mFeture;

        @Bind(R.id.owner_home_item_description)
        TextView mDescrition;

        @Bind(R.id.owner_home_item_create_time)
        TextView mCreateTime;

        @Bind(R.id.owner_home_item_owner_phone)
        TextView mOwnerPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
