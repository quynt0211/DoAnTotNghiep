package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.ReceiveDelivery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 12/03/2016.
 */

public class DeliveryShipperAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReceiveDelivery> mReceiveDeliveries;
    private OnClickAuctionButtonListenner onClickAuctionButtonListenner;

    public DeliveryShipperAdapter(Context context) {
        this.mContext = context;
        mReceiveDeliveries = new ArrayList<ReceiveDelivery>();
    }

    public void setOnClickAuctionButtonListenner(OnClickAuctionButtonListenner onClickAuctionButtonListenner) {
        this.onClickAuctionButtonListenner = onClickAuctionButtonListenner;
    }

    public void setmReceiveDeliveries(List<ReceiveDelivery> mReceiveDeliveries) {
        this.mReceiveDeliveries = mReceiveDeliveries;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mReceiveDeliveries.size();
    }

    @Override
    public ReceiveDelivery getItem(int position) {
        return mReceiveDeliveries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final ReceiveDelivery receiveDelivery = mReceiveDeliveries.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.delivery_shipper_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mPackageName.setText(receiveDelivery.getmPackageName());
        viewHolder.mOwnerName.setText(receiveDelivery.getmOwnerName());
        viewHolder.mEndLocation.setText(receiveDelivery.getmEndLocation());
        viewHolder.mStartLocation.setText(receiveDelivery.getmStartLocation());
        if (receiveDelivery.isAllowed()) {
            viewHolder.mAllowedTv.setText("Bạn Đã Được Cho Phép Đấu Giá");
            viewHolder.mAuctionBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mAllowedTv.setText("Chưa Được Cho Phép Đấu Giá");
            viewHolder.mAuctionBtn.setVisibility(View.GONE);
        }

        viewHolder.mAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAuctionButtonListenner.clickAuctionButton(receiveDelivery);
            }
        });
        return convertView;

    }

    public static class ViewHolder {
        @Bind(R.id.delivery_shipper_item_owner_name)
        TextView mOwnerName;

        @Bind(R.id.delivery_shipper_item_package_name)
        TextView mPackageName;

        @Bind(R.id.delivery_shipper_item_start_location)
        TextView mStartLocation;

        @Bind(R.id.delivery_shipper_item_end_location)
        TextView mEndLocation;

        @Bind(R.id.delivery_shipper_item_allowed)
        TextView mAllowedTv;

        @Bind(R.id.delivery_shipper_item_auction_btn)
        Button mAuctionBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickAuctionButtonListenner {
        void clickAuctionButton(ReceiveDelivery receiveDelivery);
    }
}
