package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;
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
 * <p/>
 * Created by QuyNT on 12/04/2016.
 */
public class RegisterShipperActivity extends BaseActivity {
    @Bind(R.id.register_shipper_phonenumber)
    protected EditText mPhoneExt;
    private String mPhone;

    @Bind(R.id.register_shipper_name)
    protected EditText mNameExt;
    private String mName;

    @Bind(R.id.register_shipper_address)
    protected EditText mAddressExt;
    private String mAddress;

    @Bind(R.id.register_shipper_isFragilecb)
    CheckBox mFragileCb;
    private int mFragile = 0;

    @Bind(R.id.register_shipper_isbulkyCb)
    CheckBox mBulkyCb;
    private int mBulky = 0;

    @Bind(R.id.register_shipper_isHeavyCb)
    CheckBox mHeavyCb;
    private int mHeavy = 0;

    @Bind(R.id.register_shipper_isinflammableCb)
    CheckBox mInFlammableCb;
    private int mInflammable = 0;

    @Bind(R.id.register_shipper_isSampleCb)
    CheckBox mSampleCb;
    private int mSample = 0;

    @Bind(R.id.register_shipper_description)
    EditText mDescriptionExt;
    private String mIntroduce;

    @Bind(R.id.register_shipper_password)
    protected EditText mPasswordExt;
    private String mPassword;

    @Bind(R.id.register_shipper_confirm_password)
    protected EditText mConfirmPasswordExt;
    private String mConfirmPassword;

    private static final int TIME_OUT = 3000;


    @Override
    protected int getContentView() {
        return R.layout.activity_register_shipper;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.register_shipper_activity_layout_root), this);
    }

    @OnClick(R.id.register_shipper_register_btn)
    protected void register() {
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
            DialogUtils.showMessageDialog(RegisterShipperActivity.this, error);
            return;
        }

        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();

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

        APIClient.getInstance().execPost("createShipper", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                RegisterShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                RegisterShipperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);

                    if (!errorResponse.hasError()) {
                        RegisterShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(RegisterShipperActivity.this, errorResponse.getMessage());
                            }
                        });

                        RegisterShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        RegisterShipperActivity.this.finish();
                                    }
                                }, TIME_OUT);
                            }
                        });

                    } else {
                        RegisterShipperActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(RegisterShipperActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    protected void goToHome() {
        Intent intent = new Intent(RegisterShipperActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    @OnClick(R.id.register_shipper_cancel_btn)
    protected void cancel() {
        this.finish();
    }
}
