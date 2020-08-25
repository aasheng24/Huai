package com.android.huai.http;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownLoadManager {
    private final static String TAG = "DownLoadManager";
    public final static String BASE_URL = "https://183.237.195.117:31011/ftp/";
    public final static String FILE_URL = "ftpApkUri/20200110/zhangdi/1578630597666_update.dat";


    public static void download(String baseUrl, String fileUrl) {
        Log.i(TAG, "start download ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //添加httpclient
                .client(okHttpClient)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.download(fileUrl);
        call.enqueue(callback);

    }

    private static DownloaderCallback<ResponseBody> callback = new DownloaderCallback<ResponseBody>() {
        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.i(TAG, "onFailure: " + t);
        }

        @Override
        public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.i(TAG, "onSuccess ");
            try {
                InputStream is = response.body().byteStream();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveFileToDis(is,getNameFromUrl(FILE_URL));
                        }catch (Exception e) {
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProgress(int progress) {
            Log.i(TAG, "onProgress " + progress + "%");
        }
    };


    public static void saveFileToDis(InputStream input, String fileName) throws IOException {
        File downloadFile    = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(downloadFile));
        BufferedInputStream ios              = new BufferedInputStream(input);
        byte[]              buffered         = new byte[8192];
        int                 readLen          = (ios.read(buffered));
        while (readLen != -1) {
            bf.write(buffered, 0, readLen);
            readLen = (ios.read(buffered));
        }
        bf.close();
        ios.close();
        Log.i(TAG, "下载完成");
    }

    /**
     * @return 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 下载client
     */
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response response = chain.proceed(chain.request());
                        return response.newBuilder().body(new DownloaderResponse(response.body(), callback)).build();
                    }
                }).sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), new TrustAllCerts())
                  .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                  .build();



    /**
     * 下载回调
     */
    public static abstract class DownloaderCallback<T> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            Log.i(TAG,"onResponse: "+ response);
            if (response.isSuccessful()) {
                onSuccess(call, response);
            } else {
                onFailure(call, new Throwable(response.message()));
            }
        }

        public abstract void onSuccess(Call<T> call, Response<T> response);

        public abstract void onProgress(int progress);

    }


}
