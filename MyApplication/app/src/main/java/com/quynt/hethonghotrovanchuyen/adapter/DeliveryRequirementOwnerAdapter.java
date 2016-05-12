package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Shipper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 12/03/2016.
 */
public class DeliveryRequirementOwnerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Shipper> mShippers;
    private OnClickAllowAuction onClickAllowAuction;

    public DeliveryRequirementOwnerAdapter(Context context) {
        mContext = context;
        mShippers = new ArrayList<Shipper>();
    }

    @Override
    public int getCount() {
        return mShippers.size();
    }

    public void setOnClickAllowAuction(OnClickAllowAuction onClickAllowAuction) {
        this.onClickAllowAuction = onClickAllowAuction;
    }

    public void setShippers(List<Shipper> mShippers) {
        this.mShippers = mShippers;
        notifyDataSetChanged();
    }

    @Override
    public Shipper getItem(int position) {
        return mShippers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Shipper shipper = mShippers.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.delivery_requirement_owner_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mShipperName.setText(shipper.getName());
        viewHolder.mPhoneNumber.setText(shipper.getPhoneNumber());
        viewHolder.mAddress.setText(shipper.getmAddress());
        viewHolder.mIntroduce.setText(shipper.getmIntroduce());
        viewHolder.mCanDelivery.setText(convertFeture(shipper));
        viewHolder.mAllowAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAllowAuction.onClickAllow(shipper);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.delivery_requirement_owner_item_shipper_name)
        TextView mShipperName;

        @Bind(R.id.delivery_requirement_owner_item_phonennumber)
        TextView mPhoneNumber;

        @Bind(R.id.delivery_requirement_owner_allow_auction)
        TextView mAllowAuction;

        @Bind(R.id.delivery_requirement_owner_item_address)
        TextView mAddress;

        @Bind(R.id.delivery_requirement_owner_item_introduce)
        TextView mIntroduce;

        @Bind(R.id.delivery_requirement_owner_item_can_delivery)
        TextView mCanDelivery;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }

    private StringBuilder convertFeture(Shipper mShipper) {
        StringBuilder feture = new StringBuilder();
        if (mShipper.isSamples()) {
            feture.append(" Hàng Mẫu Vật,");
        }
        if (mShipper.isBulky()) {
            feture.append(" Hàng Cồng Kềnh,");
        }
        if (mShipper.isInflammable()) {
            feture.append(" Hàng Dễ Cháy,");
        }
        if (mShipper.isFragile()) {
            feture.append(" Hàng Dễ Vỡ,");
        }
        if (mShipper.isHeavy()) {
            feture.append(" Hàng Nặng");
        }
        return feture;
    }


    public interface OnClickAllowAuction {
        void onClickAllow(Shipper shipper);
    }
}
