package com.sunsh.baselibrary.http.ok3;


import com.sunsh.baselibrary.http.HttpsUtils;
import com.sunsh.baselibrary.http.ok3.builder.GetBuilder;
import com.sunsh.baselibrary.http.ok3.builder.HeadBuilder;
import com.sunsh.baselibrary.http.ok3.builder.OtherRequestBuilder;
import com.sunsh.baselibrary.http.ok3.builder.PostFileBuilder;
import com.sunsh.baselibrary.http.ok3.builder.PostFormBuilder;
import com.sunsh.baselibrary.http.ok3.builder.PostStringBuilder;
import com.sunsh.baselibrary.http.ok3.callback.Callback;
import com.sunsh.baselibrary.http.ok3.intercepetor.LoginIntercepter;
import com.sunsh.baselibrary.http.ok3.intercepetor.RetryIntercepter;
import com.sunsh.baselibrary.http.ok3.intercepetor.log.LoggerInterceptor;
import com.sunsh.baselibrary.http.ok3.request.RequestCall;
import com.sunsh.baselibrary.http.ok3.utils.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by sunsh on 18/5/30.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 60_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private static String[] interceptors;

    public static void initInterceptors(String[] interceptors){
        if (mInstance!=null) new IllegalStateException("在获取实例前初始化");
        OkHttpUtils.interceptors = interceptors;
    }

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            if (interceptors != null && interceptors.length > 0) {
                for (int i = 0; i < interceptors.length; i++) {
                    try {
                        builder.addInterceptor((Interceptor) Class.forName(interceptors[i]).newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mOkHttpClient = builder.addInterceptor(new LoggerInterceptor(null, true))
                    .addInterceptor(new RetryIntercepter(3))
                    .addInterceptor(new LoginIntercepter())
                    .sslSocketFactory(HttpsUtils.createSSLSocketFactory(), new HttpsUtils.TrustAllManager())
                    .hostnameVerifier(new HttpsUtils.TrustAllHostnameVerifier())
                    .build();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(() -> {
            callback.onError(call, e, id);
            callback.onAfter(id);
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(() -> {
            callback.onResponse(object, id);
            callback.onAfter(id);
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

