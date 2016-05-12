package com.quynt.hethonghotrovanchuyen.fragment;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Owner;
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
 * Created by QuyNT on 15/03/2016.
 */
public class MyPageFragment extends BaseFragment {

    private String mName;
    private String mPhone;
    private String mAddress;
    private String mPassword;
    private String mConfirmPassword;

    @Bind(R.id.mypage_name)
    protected EditText mNameExt;

    @Bind(R.id.mypage_phone)
    protected EditText mPhoneExt;

    @Bind(R.id.mypage_address)
    protected EditText mAddressExt;

    @Bind(R.id.mypage_password)
    protected EditText mPasswordExt;

    @Bind(R.id.mypage_confirm_pass)
    protected EditText mConfirmPasswordExt;

    @Override
    protected int getContentView() {
        return R.layout.fragment_mypage;
    }

    @Override
    protected void initView(View view) {
        CommonUtils.dismissSoftKeyboard(view.findViewById(R.id.mypage_layout_root), getBaseActivity());
        setInfo();
    }

    private String validate() {
        if (StringUtils.isEmpty(mName)) {
            return "Vui Lòng Nhập Tên";
        }
        if (StringUtils.isEmpty(mAddress)) {
            return "Vui Lòng Nhập Địa Chỉ";
        }

        if (StringUtils.isEmpty(mPhone)) {
            return "Vui Lòng Nhập Số Điện Thoại";
        }

        if (StringUtils.isEmpty(mPassword)) {
            return "Vui Lòng Nhập Mật Khẩu";
        }

        if (StringUtils.isEmpty(mPassword)) {
            return "Vui Lòng Xác Nhận Mật Khẩu";
        }

        if (StringUtils.isEmpty(mConfirmPassword)) {
            return "Vui Lòng Xác Nhận Mật Khẩu";
        }

        if (!mPassword.equals(mConfirmPassword)) {
            return "Kiểm Tra Lại Mật Khẩu";
        }
        return "";
    }

    @OnClick(R.id.mypage_update_btn)
    protected void update() {
        mName = mNameExt.getText().toString().trim();
        mPhone = mPhoneExt.getText().toString().trim();
        mAddress = mAddressExt.getText().toString().trim();
        mPassword = mPasswordExt.getText().toString().trim();
        Log.d("mPassword", mPassword);
        mConfirmPassword = mConfirmPasswordExt.getText().toString().trim();
        Log.d("mPasswordConfirm", mConfirmPassword);

        final String error = validate();

        if (!StringUtils.isEmpty(error)) {
            DialogUtils.showMessageDialog(getBaseActivity(), error);
            return;
        }
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("id", String.valueOf(APIClient.getOwnerAccount(getBaseActivity()).getId()));
        params.put("name", mName);
        params.put("phone", mPhone);
        params.put("address", mAddress);
        params.put("password", mPassword);

        APIClient.getInstance().execPost("updateInfoOwner", params, new Callback() {
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
                    Log.d("BODY", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        saveInfo();
                    }

                    getBaseActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                        }
                    });
                }

            }
        });
    }

    private void saveInfo() {
        Owner owner = APIClient.getOwnerAccount(getBaseActivity());
        owner.setmAddress(mAddress);
        owner.setmPassword(mPassword);
        owner.setmPhoneNumber(mPhone);
        APIClient.saveLoginAccount(getBaseActivity(), owner);
        Log.d("mPhone_save", APIClient.getOwnerAccount(getBaseActivity()).getmPhoneNumber());
    }

    private void setInfo(){
        Owner owner = APIClient.getOwnerAccount(getBaseActivity());
        mNameExt.setText(owner.getName());
        mAddressExt.setText(owner.getmAddress());
        mPhoneExt.setText(owner.getmPhoneNumber());
        mPasswordExt.setText(owner.getmPassword());
        mConfirmPasswordExt.setText(owner.getmPassword());
    }

}
