package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.quynt.hethonghotrovanchuyen.utils.StringUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 08/05/2016.
 */
public class ChangeDeliveryRequirementActivity extends BaseActivity {

    @Bind(R.id.change_requirement_package_name)
    EditText mPackageNameExt;
    private String mPackageName;

    @Bind(R.id.change_requirement_start_location)
    EditText mStartLocationExt;
    private String mStartLocation;

    @Bind(R.id.change_requirement_end_location)
    EditText mEndLocationExt;
    private String mEndLocation;

    @Bind(R.id.change_requirement_isFragile)
    CheckBox mFragileCb;
    private int mFragile = 0;

    @Bind(R.id.change_requirement_isbulky)
    CheckBox mBulkyCb;
    private int mBulky = 0;

    @Bind(R.id.change_requirement_isHeavy)
    CheckBox mHeavyCb;
    private int mHeavy = 0;

    @Bind(R.id.change_requirement_isinflammable)
    CheckBox mInFlammableCb;
    private int mInflammable = 0;

    @Bind(R.id.change_requirement_isSample)
    CheckBox mSampleCb;
    private int mSample = 0;

    @Bind(R.id.change_requirement_description)
    EditText mDescriptionExt;
    private String mDescription;

    @Bind(R.id.change_requirement_number)
    EditText mNumberExt;
    private int mNumber;

    @Bind(R.id.change_requirement_weigh)
    EditText mWeighExt;
    private double mWeigh;

    PackageModel packageModel;

    private static final int TIME_OUT = 5000;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected int getContentView() {
        return R.layout.activity_change_delivery_requirement;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.create_delivery_root), this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            packageModel = (PackageModel) bundle.getSerializable("package_model");
            Log.d("bundle", "not null");
            setPrevious();
        }


    }

    @OnClick(R.id.cancel_requirement)
    protected void cancel() {
        this.finish();
    }

    private void setPrevious() {
        mPackageNameExt.setText(packageModel.getmPackageName());
        mStartLocationExt.setText(packageModel.getmStartLocation());
        mEndLocationExt.setText(packageModel.getmEndLocation());

        mInFlammableCb.setChecked(packageModel.isFlammable());
        mSampleCb.setChecked(packageModel.isSample());
        mBulkyCb.setChecked(packageModel.isBulky());
        mHeavyCb.setChecked(packageModel.isHeavy());
        mFragileCb.setChecked(packageModel.isFragile());

        mDescriptionExt.setText(packageModel.getmDescription());
        mNumberExt.setText(String.valueOf(packageModel.getmNumber()));
        mWeighExt.setText(String.valueOf(packageModel.getmWeigh()));
    }

    @OnClick(R.id.change_requirement_update)
    protected void updatePackage() {
        mPackageName = mPackageNameExt.getText().toString().trim();
        mStartLocation = mStartLocationExt.getText().toString().trim();
        mEndLocation = mEndLocationExt.getText().toString().trim();
        mDescription = mDescriptionExt.getText().toString().trim();
        if (StringUtils.isEmpty(mNumberExt.getText().toString())) {
            mNumber = 0;
        } else
            mNumber = Integer.parseInt(mNumberExt.getText().toString());
        if (StringUtils.isEmpty(mWeighExt.getText().toString()))
            mWeigh = 0.0;
        else
            mWeigh = Double.parseDouble(mWeighExt.getText().toString());

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
            DialogUtils.showMessageDialog(ChangeDeliveryRequirementActivity.this, error);
            return;
        }

        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();

        params.put("name", mPackageName);
        params.put("isinflammable", String.valueOf(mInflammable));
        params.put("isfragile", String.valueOf(mFragile));
        params.put("isbulky", String.valueOf(mBulky));
        params.put("isheavy", String.valueOf(mHeavy));
        params.put("issamples", String.valueOf(mSample));
        params.put("status", "");
        params.put("createtime", dateFormat.format(new Date()));
        params.put("startlocation", mStartLocation);
        params.put("endlocation", mEndLocation);
        params.put("number", String.valueOf(mNumber));
        params.put("weigh", String.valueOf(mWeigh));
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idowner", String.valueOf(APIClient.getOwnerAccount(this).getId()));

        APIClient.getInstance().execPost("updateDeliveryRequirement", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                ChangeDeliveryRequirementActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                ChangeDeliveryRequirementActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    ChangeDeliveryRequirementActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(ChangeDeliveryRequirementActivity.this, errorResponse.getMessage());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ChangeDeliveryRequirementActivity.this.setResult(Activity.RESULT_OK);
                                    ChangeDeliveryRequirementActivity.this.finish();
                                }
                            }, TIME_OUT);
                        }
                    });
                }
            }
        });

    }


    private String validate() {
        if (StringUtils.isEmpty(mPackageName)) {
            return "Nhập Tên Gói Hàng";
        }
        if (StringUtils.isEmpty(mStartLocation)) {
            return "Nhập Vị Trí Gửi";
        }
        if (StringUtils.isEmpty(mEndLocation)) {
            return "Nhập Vị Trí Gửi Đến";
        }
        return "";
    }

}
