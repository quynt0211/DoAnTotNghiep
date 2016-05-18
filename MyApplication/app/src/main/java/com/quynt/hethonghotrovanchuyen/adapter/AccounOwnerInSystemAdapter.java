package com.quynt.hethonghotrovanchuyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Owner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 14/05/2016.
 */
public class AccounOwnerInSystemAdapter extends BaseAdapter {
    private Context context;
    private List<Owner> mOwners;
    private OnButtonClickListenner onButtonClickListenner;

    public AccounOwnerInSystemAdapter(Context context) {
        this.context = context;
        mOwners = new ArrayList<Owner>();
    }

    public void setOnButtonClickListenner(OnButtonClickListenner onButtonClickListenner) {
        this.onButtonClickListenner = onButtonClickListenner;
    }

    public void setOwners(List<Owner> mOwners) {
        this.mOwners = mOwners;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mOwners.size();
    }

    @Override
    public Owner getItem(int position) {
        return mOwners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Owner owner = mOwners.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_account_owner_in_system, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mOwnerInSystemName.setText(owner.getName());
        viewHolder.mOwnerInSystemAddress.setText(owner.getmAddress());
        viewHolder.mOwnerInSystemPhone.setText(owner.getmPhoneNumber());
        viewHolder.mOwnerInSystemPassword.setText(owner.getmPassword());

        viewHolder.mOwnerInSystemAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListenner.onBlockClick(owner);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.item_account_owner_in_system_owner_name)
        TextView mOwnerInSystemName;

        @Bind(R.id.item_account_owner_in_system_owner_phone)
        TextView mOwnerInSystemPhone;

        @Bind(R.id.item_account_owner_in_system_owner_address)
        TextView mOwnerInSystemAddress;

        @Bind(R.id.item_account_owner_in_system_owner_password)
        TextView mOwnerInSystemPassword;

        @Bind(R.id.item_accont_owner_in_system_owner_allow_register)
        Button mOwnerInSystemAllow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public interface OnButtonClickListenner {
        void onBlockClick(Owner owner);
    }
}
