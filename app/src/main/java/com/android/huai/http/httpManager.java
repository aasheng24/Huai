package com.android.huai.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class httpManager {
    private static Retrofit mRetrofit;
    private static final String BASE_URL = "http://www.wanandroid.com/";

    private static HashMap<String, Retrofit> mRetrofits = new HashMap<>();

    public static Retrofit getRetrofit(String url){
        Retrofit retrofit = null;
        if (mRetrofit != null) {
            retrofit = mRetrofits.get(url);
        }
        if (retrofit == null) {
            synchronized (httpManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            //添加httpclient
                            //.client(okHttpClient)
                            .build();
                    mRetrofits.put(url,retrofit);
                }
            }
        }
        return retrofit;
    }

    /**
     * 自定义OkHttpClient
     */
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //设置统一的header
                    Request originalRequest = chain.request();
                    Request request = originalRequest.newBuilder()
                            .header("token","123")
                            .header("sign","456")
                            .build();
                    return chain.proceed(request);
                }
            }).build();
}
