package com.quynt.hethonghotrovanchuyen.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.model.Owner;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.InfoOwnerResponse;
import com.quynt.hethonghotrovanchuyen.model.response.InfoPackageResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
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
 * Created by Paditech on 13/05/2016.
 */
public class DetailNewPackageActivity extends BaseActivity {

    private PackageModel packageModel;
    private Owner mOwner;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Bind(R.id.detail_new_package_owner_name)
    protected TextView mOwnerName;

    @Bind(R.id.detail_new_package_owner_phone)
    protected TextView mOwnerPhone;

    @Bind(R.id.detail_new_package_owner_address)
    protected TextView mOwnerAddress;

    @Bind(R.id.detail_new_package_package_name)
    protected TextView mPackageName;

    @Bind(R.id.detail_new_package_start_location)
    protected TextView mStartLocation;

    @Bind(R.id.detail_new_package_end_location)
    protected TextView mEndLocation;

    @Bind(R.id.detail_new_package_create_time)
    protected TextView mCreateTime;

    @Bind(R.id.detail_new_package_future)
    protected TextView mPackageFuture;

    @Bind(R.id.detail_new_package_description)
    protected TextView mDescription;

    @Bind(R.id.detail_new_package_receive_delivery_btn)
    protected TextView mDeliveryBtn;

    private static final int TIME_OUT = 3000;

    @Override
    protected int getContentView() {
        return R.layout.activity_detail_new_package;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int idowner = bundle.getInt("idowner");
            int idpackage = bundle.getInt("idpackage");
            getInfoOwner(idowner);
            getInfoPackage(idpackage);
        }
    }

    private void getInfoOwner(int idowner) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idowner", String.valueOf(idowner));

        APIClient.getInstance().execPost("getInfoOwner", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        final InfoOwnerResponse infoOwnerResponse = new Gson().fromJson(body, InfoOwnerResponse.class);
                        DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mOwner = infoOwnerResponse.getOwner();
                                setInfoOwner(infoOwnerResponse.getOwner());
                            }
                        });
                    } else {
                        DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(DetailNewPackageActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    private void getInfoPackage(int idpackage) {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(idpackage));

        APIClient.getInstance().execPost("getDetailPackage", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        final InfoPackageResponse infoPackageResponse = new Gson().fromJson(body, InfoPackageResponse.class);
                        DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                packageModel = infoPackageResponse.getPackage();
                                setInfoPackage(infoPackageResponse.getPackage());
                            }
                        });
                    } else {
                        DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(DetailNewPackageActivity.this, errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    private void setInfoOwner(Owner owner) {
        mOwnerName.setText(owner.getName());
        mOwnerAddress.setText(owner.getmAddress());
        mOwnerPhone.setText(owner.getmPhoneNumber());
    }

    private void setInfoPackage(PackageModel packageModel) {
        mPackageName.setText(packageModel.getmPackageName());
        mStartLocation.setText(packageModel.getmStartLocation());
        mEndLocation.setText(packageModel.getmEndLocation());
        mCreateTime.setText(packageModel.getmCreateTime());
        mDescription.setText(packageModel.getmDescription());
        mPackageFuture.setText(convertFeture(packageModel));
    }

    private StringBuilder convertFeture(PackageModel mPackage) {
        StringBuilder feture = new StringBuilder();
        if (mPackage.isSample()) {
            feture.append(" Hàng Mẫu Vật,");
        }
        if (mPackage.isBulky()) {
            feture.append(" Hàng Cồng Kềnh,");
        }
        if (mPackage.isFlammable()) {
            feture.append(" Hàng Dễ Cháy,");
        }
        if (mPackage.isFragile()) {
            feture.append(" Hàng Dễ Vỡ,");
        }
        if (mPackage.isHeavy()) {
            feture.append(" Hàng Nặng");
        }
        return feture;
    }

    @OnClick(R.id.detail_new_package_receive_delivery_btn)
    protected void clickDelivery() {
        final Dialog dialog = DialogUtils.showLoadingDialog(this);
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idowner", String.valueOf(packageModel.getmIdOwner()));
        params.put("idshipper", String.valueOf(APIClient.getShipperAccount(this).getId()));
        params.put("auctiondate", dateFormat.format(new Date()));

        Log.d("idpacage", String.valueOf(packageModel.getmIdPackage()));
        Log.d("idowner", String.valueOf(packageModel.getmIdOwner()));
        Log.d("idshipper", String.valueOf(APIClient.getShipperAccount(this).getId()));

        APIClient.getInstance().execPost("receiveDelivery", params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showMessageDialog(DetailNewPackageActivity.this, errorResponse.getMessage());
                        }
                    });

                    if (!errorResponse.hasError()) {
                        DetailNewPackageActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        DetailNewPackageActivity.this.finish();
                                    }
                                }, TIME_OUT);
                            }
                        });
                    }
                }
            }
        });
    }
}
