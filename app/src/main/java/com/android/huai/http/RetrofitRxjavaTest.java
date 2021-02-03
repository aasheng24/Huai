package com.android.huai.http;

import com.android.huai.bean.KnowledgeSystem;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitRxjavaTest {


    /**
     * "http://www.wanandroid.com/"
     */
    public static void testGet() {
        Retrofit retrofit = RetrofitManager.getRetrofit("http://www.wanandroid.com/");
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getKnowledgeSystemCall(1,1)
                .enqueue(new Callback<KnowledgeSystem>() {
                    @Override
                    public void onResponse(Call<KnowledgeSystem> call, Response<KnowledgeSystem> response) {

                    }

                    @Override
                    public void onFailure(Call<KnowledgeSystem> call, Throwable t) {

                    }
                });
        apiService.getKnowledgeSystem(1,1)
                .subscribeOn(Schedulers.io())//IO线程显示数据
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KnowledgeSystem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KnowledgeSystem knowledgeSystem) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
