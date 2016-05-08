package com.quynt.hethonghotrovanchuyen.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.R;
import com.quynt.hethonghotrovanchuyen.activity.AuctionOwnerActivity;
import com.quynt.hethonghotrovanchuyen.activity.ChangeDeliveryRequirementActivity;
import com.quynt.hethonghotrovanchuyen.activity.CreateDeliveryRequirementActivity;
import com.quynt.hethonghotrovanchuyen.activity.DeliveryRequirementOwnerActivity;
import com.quynt.hethonghotrovanchuyen.adapter.OwnerHistoryAdapter;
import com.quynt.hethonghotrovanchuyen.model.PackageModel;
import com.quynt.hethonghotrovanchuyen.model.response.ErrorResponse;
import com.quynt.hethonghotrovanchuyen.model.response.OwnerHistoryResponse;
import com.quynt.hethonghotrovanchuyen.utils.APIClient;
import com.quynt.hethonghotrovanchuyen.utils.Const;
import com.quynt.hethonghotrovanchuyen.utils.DialogUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * He Thong Ho Tro Van Chuyen
 * <p>
 * Created by QuyNT on 15/03/2016.
 */
public class HistoryFragment extends BaseFragment implements OwnerHistoryAdapter.OnClickListenner {
    @Bind(R.id.history_owner)
    ListView mOwnerHistorys;

    OwnerHistoryAdapter ownerHistoryAdapter;

    List<PackageModel> mPackageHistory;

    @Override
    protected int getContentView() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View view) {
        initListView();
        getHistory();
        mPackageHistory = new ArrayList<PackageModel>();
    }

    private void initListView() {
        ownerHistoryAdapter = new OwnerHistoryAdapter(getBaseActivity());
        ownerHistoryAdapter.setOnClickListenner(this);
        mOwnerHistorys.setAdapter(ownerHistoryAdapter);
    }

    @Override
    public void onDestroy() {
        Log.d("history", "ondestroy");
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("history", "onDetach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("history", "OnStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("history", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("history", "OnStop");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("history", "onAttact");
    }

    @Override
    public void onDestroyView() {
        Log.d("history", "ondestroyview");
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Const.HISTORY_CHANGE_REQUIREMENT){
            if(resultCode == Activity.RESULT_OK){
                getHistory();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onChangeRequirement(PackageModel packageModel) {
        Intent intent = new Intent(getBaseActivity(), ChangeDeliveryRequirementActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("package_model", packageModel);
        intent.putExtras(bundle);
        startActivityForResult(intent, Const.HISTORY_CHANGE_REQUIREMENT);
    }

    @Override
    public void onDeleteRequirement(int idPackage, int position) {
        //showDialogConfirm(idPackage, position);
        remove(idPackage, position);
    }


    @Override
    public void onViewShipper() {
        Intent intent = new Intent(getBaseActivity(), DeliveryRequirementOwnerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewAuction() {
        Intent intent = new Intent(getBaseActivity(), AuctionOwnerActivity.class);
        startActivity(intent);
    }

    private void getHistory() {
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("id", String.valueOf(APIClient.getOwnerAccount(getBaseActivity()).getId()));

        APIClient.getInstance().execPost("getHistoryOwner", params, new Callback() {
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
                    if (errorResponse.hasError()) {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    } else {
                        final OwnerHistoryResponse ownerHistoryResponse = new Gson().fromJson(body, OwnerHistoryResponse.class);
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPackageHistory = ownerHistoryResponse.getPackage();
                                ownerHistoryAdapter.setPackages(mPackageHistory);
                            }
                        });
                    }

                }
            }
        });
    }

    private void showDialogConfirm(final int idPackage, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("Xác Nhận Hủy")
                .setMessage("Bạn Có Muốn Hủy Yêu Cầu Giao Hàng")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(idPackage, position);
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void remove(int idPackage, final int position) {
        final Dialog dialog = DialogUtils.showLoadingDialog(getBaseActivity());
        dialog.show();

        SortedMap<String, String> params = new TreeMap<>();
        params.put("idowner", String.valueOf(APIClient.getOwnerAccount(getBaseActivity()).getId()));
        params.put("idpackage", String.valueOf(idPackage));

        APIClient.getInstance().execPost("removeDeliveryRequirement", params, new Callback() {
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
                    Log.d("history_response", body);
                    Log.d("errorresponse", errorResponse.getMessage() + errorResponse.hasError());
                    if (errorResponse.hasError()) {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    } else {
                        getBaseActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPackageHistory.remove(position);
                                Log.d("history_package", mPackageHistory.size() +" size");
                                ownerHistoryAdapter.setPackages(mPackageHistory);
                                DialogUtils.showMessageDialog(getBaseActivity(), errorResponse.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }
}
