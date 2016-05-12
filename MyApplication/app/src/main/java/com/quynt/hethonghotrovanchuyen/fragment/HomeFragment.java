package com.quynt.hethonghotrovanchuyen.fragment;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.adapter.HomeAdapter;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.OwnerHomeResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 15/04/2016.
 */
public class HomeFragment extends BaseFragment implements HomeAdapter.OnClickDelivery {
    @Bind(R.id.home_listview)
    ListView mPostList;
    HomeAdapter homeAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        setupList();
        getDeliveryRequirements();
    }

    private void setupList() {
        homeAdapter = new HomeAdapter(getBaseActivity());
        homeAdapter.setOnClickDelivery(this);
        mPostList.setAdapter(homeAdapter);
    }


    private void getDeliveryRequirements() {
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();

        APIClient.getInstance().execPost("getDeliveryRequirements", params, new Callback() {
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
                    Log.d("Home_Fragment", body);
                    final ErrorResponse errorResponse = new Gson().fromJson(body, ErrorResponse.class);
                    if (!errorResponse.hasError()) {
                        final OwnerHomeResponse ownerHomeResponse = new Gson().fromJson(body, OwnerHomeResponse.class);
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int size = ownerHomeResponse.getPackage().size();
                                if(size == 0 ){
                                    DialogUtils.showMessageDialog(getBaseActivity(), "Không Có Gói Hàng Mới Nào");
                                }
                                homeAdapter.setPackages(ownerHomeResponse.getPackage());
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
    public void clickDelivery(PackageModel packageModel) {
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idpackage", String.valueOf(packageModel.getmIdPackage()));
        params.put("idowner", String.valueOf(packageModel.getmIdOwner()));
        params.put("idshipper", String.valueOf(APIClient.getShipperAccount(getBaseActivity()).getId()));

        Log.d("idpacage", String.valueOf(packageModel.getmIdPackage()));
        Log.d("idowner", String.valueOf(packageModel.getmIdOwner()));
        Log.d("idshipper", String.valueOf(APIClient.getShipperAccount(getBaseActivity()).getId()));

        APIClient.getInstance().execPost("receiveDelivery", params, new Callback() {
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
}
