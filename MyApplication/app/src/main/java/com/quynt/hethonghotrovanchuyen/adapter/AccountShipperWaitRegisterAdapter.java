package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Shipper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 19/05/2016.
 */
public class AccountShipperWaitRegisterAdapter extends BaseAdapter {
    private Context mContext;
    private List<Shipper> mShipper;
    private OnButtonClickListenner onButtonClickListenner;

    public AccountShipperWaitRegisterAdapter(Context context) {
        mContext = context;
        mShipper = new ArrayList<Shipper>();
    }

    public void setOnButtonClickListenner(OnButtonClickListenner onButtonClickListenner) {
        this.onButtonClickListenner = onButtonClickListenner;
    }

    public void setShippers(List<Shipper> shippers) {
        this.mShipper = shippers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mShipper.size();
    }

    @Override
    public Shipper getItem(int position) {
        return mShipper.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Shipper shipper = mShipper.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_account_shipper_wait_register, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mShipperName.setText(shipper.getName());
        viewHolder.mShipperPhone.setText(shipper.getPhoneNumber());
        viewHolder.mAddress.setText(shipper.getmAddress());
        viewHolder.mIntroduce.setText(shipper.getmIntroduce());
        viewHolder.mCanDelivery.setText(convertFeture(shipper));
        viewHolder.mBlockAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListenner.onBlockAccount(shipper);
            }
        });

        viewHolder.mAllowRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListenner.onAllowRegister(shipper);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.item_account_shipper_in_wait_register_name)
        TextView mShipperName;

        @Bind(R.id.item_account_shipper_wait_register_phone)
        TextView mShipperPhone;

        @Bind(R.id.item_account_shipper_wait_register_introduce)
        TextView mIntroduce;

        @Bind(R.id.item_account_shipper_wait_register_can_delivery)
        TextView mCanDelivery;

        @Bind(R.id.item_account_shippr_wait_register_address)
        TextView mAddress;

        @Bind(R.id.item_account_shipper_wait_register_block_account)
        Button mBlockAccount;

        @Bind(R.id.item_account_shipper_wait_register_allow_register)
        Button mAllowRegister;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnButtonClickListenner {
        void onBlockAccount(Shipper shipper);

        void onAllowRegister(Shipper shipper);
    }

    private StringBuilder convertFeture(Shipper shipper) {
        StringBuilder feture = new StringBuilder();
        if (shipper.isSamples()) {
            feture.append("Hàng Mẫu Vật ");
        }
        if (shipper.isBulky()) {
            feture.append("Hàng Cồng Kềnh ");
        }
        if (shipper.isInflammable()) {
            feture.append("Hàng Dễ Cháy ");
        }
        if (shipper.isFragile()) {
            feture.append("Hàng Dễ Vỡ ");
        }
        if (shipper.isHeavy()) {
            feture.append("Hàng Nặng");
        }
        return feture;
    }
}
