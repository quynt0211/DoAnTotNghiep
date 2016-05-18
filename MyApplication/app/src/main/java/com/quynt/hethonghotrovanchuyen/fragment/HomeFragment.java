package com.quynt.hethonghotrovanchuyen.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.activity.DetailNewPackageActivity;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 15/04/2016.
 */
public class HomeFragment extends BaseFragment implements HomeAdapter.OnClickDelivery {
    @Bind(R.id.home_listview)
    ListView mPostList;
    HomeAdapter homeAdapter;

    @Bind(R.id.home_shipper_empty)
    TextView mHomeShipperEmpty;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<PackageModel> packageModels;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        setupList();
        getDeliveryRequirements();
        getNotification();
    }

    private void setupList() {
        packageModels = new ArrayList<PackageModel>();
        homeAdapter = new HomeAdapter(getBaseActivity());
        homeAdapter.setOnClickDelivery(this);
        mPostList.setAdapter(homeAdapter);
        mPostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToDetail(packageModels.get(position).getmIdPackage(), packageModels.get(position).getmIdOwner());
            }
        });
    }

    private void getNotification() {
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idshipper", String.valueOf(APIClient.getShipperAccount(getBaseActivity()).getId()));

        APIClient.getInstance().execPost("getNotificationShipper", params, new Callback() {
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
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
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
                                if (size == 0) {
                                    mHomeShipperEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    mHomeShipperEmpty.setVisibility(View.GONE);
                                }
                                packageModels = ownerHomeResponse.getPackage();
                                homeAdapter.setPackages(packageModels);
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
        params.put("auctiondate", dateFormat.format(new Date()));

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

    private void goToDetail(int idpackage, int idowner) {
        Intent intent = new Intent(getBaseActivity(), DetailNewPackageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idpackage", idpackage);
        bundle.putInt("idowner", idowner);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
