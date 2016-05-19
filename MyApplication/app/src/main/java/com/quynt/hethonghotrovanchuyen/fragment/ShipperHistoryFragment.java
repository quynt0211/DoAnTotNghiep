package com.quynt.hethonghotrovanchuyen.fragment;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.ShipperHistoryAdapter;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.OwnerHomeResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
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

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 18/04/2016.
 */
public class ShipperHistoryFragment extends BaseFragment implements ShipperHistoryAdapter.OnClickButtonListenner {
    @Bind(R.id.history_shipper)
    protected ListView mShipperHistory;
    ShipperHistoryAdapter shipperHistoryAdapter;

    @Bind(R.id.shipper_history_no_history)
    TextView mNoHistory;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected int getContentView() {
        return R.layout.fragment_shipper_history;
    }

    @Override
    protected void initView(View view) {
        setupListView();
        getHistorySHipper();
    }

    private void setupListView() {
        shipperHistoryAdapter = new ShipperHistoryAdapter(getBaseActivity());
        shipperHistoryAdapter.setOnClickButtonListenner(this);
        mShipperHistory.setAdapter(shipperHistoryAdapter);
    }


    private void getHistorySHipper() {
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("id", String.valueOf(APIClient.getShipperAccount(getBaseActivity()).getId()));

        APIClient.getInstance().execPost("getHistoryShipper", params, new Callback() {
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
                        final OwnerHomeResponse ownerHomeResponse = new Gson().fromJson(body, OwnerHomeResponse.class);
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int size = ownerHomeResponse.getPackage().size();
                                if (size == 0) {
                                    //DialogUtils.showMessageDialog(getBaseActivity(),  "Bạn Chưa Đi Giao Hàng");
                                    mNoHistory.setVisibility(View.VISIBLE);
                                } else {
                                    mNoHistory.setVisibility(View.GONE);
                                }
                                shipperHistoryAdapter.setPackages(ownerHomeResponse.getPackage());
                            }
                        });
                    } else {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void clickUpdateLocation(PackageModel packageModel, String location) {
        if (StringUtils.isEmpty(location)) {
            DialogUtils.showMessageDialog(getBaseActivity(), "Vui Lòng Nhập Vị Trí Hiện Tại Của Gói Hàng");
            return;
        }

        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idshipper", String.valueOf(APIClient.getShipperAccount(getBaseActivity()).getId()));
        params.put("location", location);
        params.put("updatetime", dateFormat.format(new Date()));

        APIClient.getInstance().execPost("updateLocation", params, new Callback() {
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
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getHistorySHipper();
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    } else {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    }
                }

            }
        });
    }

    @Override
    public void clickUpdateStatus(PackageModel packageModel, String status) {
        if (StringUtils.isEmpty(status)) {
            DialogUtils.showMessageDialog(getBaseActivity(), "Vui Lòng Nhập Vị Trí Hiện Tại Của Gói Hàng");
            return;
        }

        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idshipper", String.valueOf(APIClient.getShipperAccount(getBaseActivity()).getId()));
        params.put("status", status);
        params.put("updatetime", dateFormat.format(new Date()));

        APIClient.getInstance().execPost("updateStatusPackage", params, new Callback() {
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
                    Log.d("body_status", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getHistorySHipper();
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    } else {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    }
                }

            }
        });
    }

}
