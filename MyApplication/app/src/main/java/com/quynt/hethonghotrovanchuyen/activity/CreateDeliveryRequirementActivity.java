package com.quynt.hethonghotrovanchuyen.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.utils.CommonUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 12/03/2016.
 */
public class CreateDeliveryRequirementActivity extends BaseActivity {

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
    private int mFragile;

    @Bind(R.id.change_requirement_isbulky)
    CheckBox mBulkyCb;
    private int mBulky;

    @Bind(R.id.change_requirement_isHeavy)
    CheckBox mHeavyCb;
    private int mHeavy;

    @Bind(R.id.change_requirement_isinflammable)
    CheckBox mInFlammableCb;
    private int mInflammable;

    @Bind(R.id.change_requirement_isSample)
    CheckBox mSampleCb;
    private int mSample;

    @Bind(R.id.change_requirement_description)
    EditText mDescriptionExt;

    @Bind(R.id.change_requirement_number)
    EditText mNumberExt;
    private int mNumber;

    @Bind(R.id.change_requirement_weigh)
    EditText mWeighExt;
    private double mWeigh;

    PackageModel packageModel;


    @Override
    protected int getContentView() {
        return R.layout.activity_create_delivery_requirement;
    }

    @Override
    protected void initView() {
        CommonUtils.dismissSoftKeyboard(findViewById(R.id.create_delivery_root), this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            packageModel = (PackageModel) bundle.getSerializable("model");
        }
    }

    @OnClick(R.id.cancel_requirement)
    protected void cancel() {
        this.finish();
    }
}
