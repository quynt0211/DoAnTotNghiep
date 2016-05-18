package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.content.Intent;
import android.widget.EditText;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
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
 * <p/>
 * Created by QuyNT on 14/05/2016.
 */
public class LoginAdminPageActivity extends BaseActivity {
    @Bind(R.id.login_admin_id)
    EditText mIdExt;
    private String mID = "";

    @Bind(R.id.login_admin_password)
    EditText mPasswordExt;
    private String mPassword;

    @Override
    protected int getContentView() {
        return R.layout.activity_login_admin_page;
    }

    @Override
    protected void initView() {

    }

    private String validate() {
        if (StringUtils.isEmpty(mID)) {
            return "Vui Lòng Nhập ID";
        }
        if (StringUtils.isEmpty(mPassword)) {
            return "Vui Lòng Nhập Password";
        }
        return "";
    }

    @OnClick(R.id.login_admin_page_login_button)
    protected void login() {
        mID = mIdExt.getText().toString().trim();
        mPassword = mPasswordExt.getText().toString().trim();
        final String error = validate();
        if (!StringUtils.isEmpty(error)) {
            DialogUtils.showMessageDialog(this, error);
            return;
        }


        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("id", mID);
        params.put("password", mPassword);

        APIClient.getInstance().execPost("loginAdmin", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LoginAdminPageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                LoginAdminPageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        goToAdminPage();
                    } else {
                        LoginAdminPageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(LoginAdminPageActivity.this, errorResponse.getMessage());
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    private void goToAdminPage() {
        Intent intent = new Intent(this, AdminPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
