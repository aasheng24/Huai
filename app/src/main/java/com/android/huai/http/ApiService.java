package com.android.huai.http;

import com.android.huai.bean.KnowledgeSystem;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    /**
     * http://www.wanandroid.com/
     * 获取只是体系数据
     */
    @GET("article/list/{page}/json")
    Observable<KnowledgeSystem> getKnowledgeSystem(@Path("page")int page, @Query("cid")int cid);


    /**
     * http://www.wanandroid.com/
     * 获取只是体系数据
     */
    @GET("article/list/{page}/json")
    Call<KnowledgeSystem> getKnowledgeSystemCall(@Path("page")int page, @Query("cid")int cid);

    /**
     * 下载文件
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);
}
