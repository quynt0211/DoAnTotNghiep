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
public class AuctionShipperAdapter extends BaseAdapter {
    private Context context;
    private List<ReceiveDelivery> mReceiveDeliveries;
    private OnClickUpdateAuction onClickUpdateAuction;

    public AuctionShipperAdapter(Context context) {
        this.context = context;
        mReceiveDeliveries = new ArrayList<ReceiveDelivery>();
    }

    public void setmReceiveDeliveries(List<ReceiveDelivery> mReceiveDeliveries) {
        this.mReceiveDeliveries = mReceiveDeliveries;
        notifyDataSetChanged();
    }

    public void setOnClickUpdateAuction(OnClickUpdateAuction onClickUpdateAuction) {
        this.onClickUpdateAuction = onClickUpdateAuction;
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
        final ReceiveDelivery receiveDelivery = mReceiveDeliveries.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.auction_shipper_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mOwnerName.setText(receiveDelivery.getmOwnerName());
        viewHolder.mPackageName.setText(receiveDelivery.getmPackageName());
        viewHolder.mEndLocation.setText(receiveDelivery.getmEndLocation());
        viewHolder.mStartLocation.setText(receiveDelivery.getmStartLocation());
        viewHolder.mRate.setText(String.valueOf(receiveDelivery.getRate()));
        viewHolder.mUpdateAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateAuction.clickUpdateAuction(receiveDelivery);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.auction_shipper_item_owner_name)
        TextView mOwnerName;

        @Bind(R.id.auction_shipper_item_package_name)
        TextView mPackageName;

        @Bind(R.id.auction_shipper_item_start_location)
        TextView mStartLocation;

        @Bind(R.id.auction_shipper_item_end_location)
        TextView mEndLocation;

        @Bind(R.id.auction_shipper_item_rate)
        TextView mRate;

        @Bind(R.id.auction_shipper_item_update_auctionbtn)
        Button mUpdateAuctionBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickUpdateAuction {
        void clickUpdateAuction(ReceiveDelivery receiveDelivery);
    }
}
