package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Auction;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
 */
public class AuctionOwnerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Auction> mAuctions;
    private OnClickAllowDelivery onClickAllowDelivery;

    public AuctionOwnerAdapter(Context context) {
        this.mContext = context;
        mAuctions = new ArrayList<Auction>();
    }

    public void setAuctions(List<Auction> mAuctions) {
        this.mAuctions = mAuctions;
        notifyDataSetChanged();
    }

    public void setOnClickAllowDelivery(OnClickAllowDelivery onClickAllowDelivery) {
        this.onClickAllowDelivery = onClickAllowDelivery;
    }

    @Override
    public int getCount() {
        return mAuctions.size();
    }

    @Override
    public Auction getItem(int position) {
        return mAuctions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Auction auction = mAuctions.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.auction_owner_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mName.setText(auction.getmName());
        viewHolder.mPhoneNumber.setText(auction.getmPhoneNumber());
        viewHolder.mAuctionDate.setText(auction.getmAuctionDate());
        viewHolder.mCanDelivery.setText(convertFeture(auction));
        if (Double.compare(auction.getmRate(), -1.0) == 0) {
            viewHolder.mRate.setText("Chưa Đặt Giá");
            viewHolder.mAllowDelivery.setEnabled(false);
            viewHolder.mAllowDelivery.setVisibility(View.GONE);
        } else {
            viewHolder.mRate.setText(String.valueOf(auction.getmRate()));
            viewHolder.mAllowDelivery.setEnabled(true);
            viewHolder.mAllowDelivery.setVisibility(View.VISIBLE);
        }

        viewHolder.mAllowDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAllowDelivery.onClickAllow(auction);
            }
        });
        return convertView;
    }

    private StringBuilder convertFeture(Auction auction) {
        StringBuilder feture = new StringBuilder();
        if (auction.isSample()) {
            feture.append(" Hàng Mẫu Vật,");
        }
        if (auction.isBulky()) {
            feture.append(" Hàng Cồng Kềnh,");
        }
        if (auction.isInflammable()) {
            feture.append(" Hàng Dễ Cháy,");
        }
        if (auction.isFragile()) {
            feture.append(" Hàng Dễ Vỡ,");
        }
        if (auction.isHeavy()) {
            feture.append(" Hàng Nặng");
        }
        return feture;
    }

    public static class ViewHolder {
        @Bind(R.id.auction_owner_item_name)
        TextView mName;

        @Bind(R.id.auction_owner_item_phone)
        TextView mPhoneNumber;

        @Bind(R.id.auction_owner_item_rate)
        TextView mRate;

        @Bind(R.id.auction_owner_item_can_delivery)
        TextView mCanDelivery;

        @Bind(R.id.auction_owner_item_auction_date)
        TextView mAuctionDate;

        @Bind(R.id.auction_owner_item_allowed_delivery)
        Button mAllowDelivery;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickAllowDelivery {
        void onClickAllow(Auction auction);
    }
}
