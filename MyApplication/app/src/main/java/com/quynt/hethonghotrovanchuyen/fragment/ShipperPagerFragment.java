package com.quynt.hethonghotrovanchuyen.fragment;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Shipper;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.quynt.hethonghotrovanchuyen.utils.StringUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 12/03/2016.
 */
public class ShipperPagerFragment extends BaseFragment {

    @Bind(R.id.shipper_page_phone)
    protected EditText mPhoneExt;
    private String mPhone;

    @Bind(R.id.shipper_pager_name)
    protected EditText mNameExt;
    private String mName;

    @Bind(R.id.shipper_pager_address)
    protected EditText mAddressExt;
    private String mAddress;

    @Bind(R.id.shipper_pager_isFragileCb)
    CheckBox mFragileCb;
    private int mFragile = 0;

    @Bind(R.id.shipper_pager_isbulkyCb)
    CheckBox mBulkyCb;
    private int mBulky = 0;

    @Bind(R.id.shipper_pager_isHeavyCb)
    CheckBox mHeavyCb;
    private int mHeavy = 0;

    @Bind(R.id.shipper_pager_isinflammableCb)
    CheckBox mInFlammableCb;
    private int mInflammable = 0;

    @Bind(R.id.shipper_pager_isSampleCb)
    CheckBox mSampleCb;
    private int mSample = 0;

    @Bind(R.id.shipper_pager_introduce)
    EditText mDescriptionExt;
    private String mIntroduce;

    @Bind(R.id.shipper_pager_password)
    protected EditText mPasswordExt;
    private String mPassword;

    @Bind(R.id.shipper_pager_confirm_password)
    protected EditText mConfirmPasswordExt;
    private String mConfirmPassword;

    private static final int TIME_OUT = 3000;

    @Override
    protected int getContentView() {
        return R.layout.fragment_shipper_pager;
    }

    @Override
    protected void initView(View view) {
        setInfo();
    }

    private String validate() {
        if (StringUtils.isEmpty(mName)) {
            return "Vui Lòng Nhập Tên Của Bạn";
        }
        if (StringUtils.isEmpty(mPhone)) {
            return "Vui Lòng Nhập Số Điện Thoại Của Bạn";
        }

        if (StringUtils.isEmpty(mAddress)) {
            return "Vui Lòng Nhập Địa Chỉ Của Bạn";
        }

        if (StringUtils.isEmpty(mPassword)) {
            return "Vui Lòng Nhập Mật Khẩu Của Bạn";
        }

        if (StringUtils.isEmpty(mConfirmPassword)) {
            return "Xác Nhận Mật Khẩu Của Bạn";
        }

        if (!mPassword.equals(mConfirmPassword)) {
            return "Kiểm Tra Lại Mật Khẩu";
        }

        return "";
    }

    @OnClick(R.id.shipper_pager_update_btn)
    protected void updateShipper() {
        mName = mNameExt.getText().toString().trim();
        mPhone = mPhoneExt.getText().toString().trim();
        mAddress = mAddressExt.getText().toString().trim();
        mPassword = mPasswordExt.getText().toString().trim();
        mConfirmPassword = mConfirmPasswordExt.getText().toString().trim();
        mIntroduce = mDescriptionExt.getText().toString().trim();

        if (mBulkyCb.isChecked()) {
            mBulky = 1;
        }

        if (mFragileCb.isChecked()) {
            mFragile = 1;
        }

        if (mHeavyCb.isChecked()) {
            mHeavy = 1;
        }

        if (mSampleCb.isChecked()) {
            mSample = 1;
        }
        if (mInFlammableCb.isChecked()) {
            mInflammable = 1;
        }

        String error = validate();
        if (!StringUtils.isEmpty(error)) {
            DialogUtils.showMessageDialog(getBaseActivity(), error);
            return;
        }
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idshipper", String.valueOf(APIClient.getShipperAccount(getBaseActivity()).getId()));
        params.put("name", mName);
        params.put("phone", mPhone);
        params.put("address", mAddress);
        params.put("password", mPassword);
        params.put("introduce", mIntroduce);
        params.put("isinflammable", String.valueOf(mInflammable));
        params.put("isfragile", String.valueOf(mFragile));
        params.put("isbulky", String.valueOf(mBulky));
        params.put("isheavy", String.valueOf(mHeavy));
        params.put("issamples", String.valueOf(mSample));

        APIClient.getInstance().execPost("updateInfoShipper", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        saveInfo();
                    }
                    getBaseActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            setInfo();
                        }
                    });

                }
            }
        });

    }

    private void setInfo() {
        Shipper shipper = APIClient.getShipperAccount(getBaseActivity());
        mNameExt.setText(shipper.getName());
        mDescriptionExt.setText(shipper.getmIntroduce());
        mAddressExt.setText(shipper.getmAddress());
        mPhoneExt.setText(shipper.getPhoneNumber());
        mPasswordExt.setText(shipper.getpassWord());
        mConfirmPasswordExt.setText(shipper.getpassWord());
        mBulkyCb.setChecked(shipper.isBulky());
        mInFlammableCb.setChecked(shipper.isInflammable());
        mSampleCb.setChecked(shipper.isSamples());
        mFragileCb.setChecked(shipper.isFragile());
        mHeavyCb.setChecked(shipper.isHeavy());
    }

    private void saveInfo() {
        Shipper shipper = APIClient.getShipperAccount(getBaseActivity());
        shipper.setmName(mName);
        shipper.setmPhoneNumber(mPhone);
        shipper.setmAddress(mAddress);
        shipper.setmIntroduce(mIntroduce);
        shipper.setmPassword(mPassword);
        shipper.setmBulky(mBulky);
        shipper.setmHeavy(mHeavy);
        shipper.setmFragile(mFragile);
        shipper.setmSample(mSample);
        shipper.setmInflammable(mInflammable);

        APIClient.saveLoginAccount(getBaseActivity(), shipper);
        Log.d("shipper_phone", APIClient.getShipperAccount(getBaseActivity()).getPhoneNumber());
    }
}
