package com.quynt.hethonghotrovanchuyen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.quynt.hethonghotrovanchuyen.model.Owner;
import com.quynt.hethonghotrovanchuyen.model.Shipper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okio.Buffer;

/**
 * He Thong Ho Tro Van Chuyen
 * <p/>
 * Created by QuyNT on 3/14/2016.
 */
public class APIClient {
    private static final String TAG = APIClient.class.getSimpleName();

//   private static final String BASE_URL = "http://192.168.43.25/api/";
    private static final String BASE_URL = "http://192.168.1.118/api/";
    private static final String PREF_NAME = "Shipper";

    private static APIClient instance;

    private static final OkHttpClient mHttpClient = generateDefaultOkHttp();

    private APIClient() {
        super();
    }

    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }
        return instance;
    }

    private static OkHttpClient generateDefaultOkHttp() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
        client.setRetryOnConnectionFailure(true);

        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname,
                                      final SSLSession session) {
                    return true;
                }
            };
            client.setHostnameVerifier(hostnameVerifier);
            client.setSslSocketFactory(ctx.getSocketFactory());
        } catch (final Exception e) {
        }
        return client;
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private static void savePreferences(Context context, String key, String content) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, content);
        editor.apply();
    }

    private static String getPreferences(Context context, String key) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(key, null);
    }

//    public static boolean showIntroduce(Context context) {
//        if (StringUtils.isEmpty(getPreferences(context, "app::introduce"))) {
//            savePreferences(context, "app::introduce", "hide");
//            return true;
//        }
//        return false;
//    }

    public static boolean saveLoginAccount(Context context, Owner account) {
        if (account == null) {
            return false;
        }
        String json = new Gson().toJson(account);
        savePreferences(context, "account::detail", json);
        return true;
    }

    public static boolean saveLoginAccount(Context context, Shipper account) {
        if (account == null) {
            return false;
        }
        String json = new Gson().toJson(account);
        savePreferences(context, "account::detail", json);
        return true;
    }

    public static boolean isOwner(Context context) {
        int accountType = Integer.parseInt(getAccountType(context));
        return accountType == Const.OWNER;
    }

    public static String getAccountType(Context context) {
        return getPreferences(context, "account::type");
    }

    public static void saveAccountType(Context context, int mAccountType) {
        savePreferences(context, "account::type", String.valueOf(mAccountType));
    }

    //
    public static Owner getOwnerAccount(Context context) {
        final String json = getPreferences(context, "account::detail");
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            return new Gson().fromJson(json, Owner.class);
        }
    }

    public static Shipper getShipperAccount(Context context) {
        final String json = getPreferences(context, "account::detail");
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            return new Gson().fromJson(json, Shipper.class);
        }
    }

    public static String getAuthToken(Context context) {
        final String token = getPreferences(context, "account::token");
        return token;
    }


    public static boolean isLogin(Context context) {
        return (getOwnerAccount(context) != null || getShipperAccount(context) != null);
    }

    // this is the comment to deo git responsity

    public static void removeAccount(Context context) {
        final SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.remove("account::detail");
        editor.apply();
    }

    static String getResourceURL(String resource) {
        return String.format("%s%s", BASE_URL, resource);
    }

    public static JSONObject extractData(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("");
    }

    static String makeQueryString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }

            try {
                sb.append(URLEncoder.encode(entry.getKey(), "utf-8")).append("=")
                        .append(URLEncoder.encode(entry.getValue(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                Log.w(TAG, "Encode Error", e);
            }

        }

        if (sb.length() > 0) {
            sb.insert(0, "?");
            return sb.toString();
        } else {
            return "";
        }

    }

    private static String bodyToString(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    // temp add comment to apiclient file on 251 line

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private void execMethod(String url, String method, RequestBody body, Callback callback) {
        Log.i(TAG, String.format("method=%s, url=%s", method, url));
        final Request.Builder rb = new Request.Builder().url(url).method(method, body)
                .addHeader("Accept", "application/json")
                .addHeader("Accept", "text/html")
                .addHeader("Accept", "application/xhtml+xml")
                .addHeader("Accept", "application/xml;q=0.9,*/*;q=0.8 ");
        mHttpClient.newCall(rb.build()).enqueue(callback);
    }


    public void execPost(String uri, SortedMap<String, String> params, Callback callback) {
        FormEncodingBuilder fb = new FormEncodingBuilder();
        for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            String value = params.get(key);
            fb.add(key, value);
        }
        execMethod(getResourceURL(uri), "POST", fb.build(), callback);
    }
}
