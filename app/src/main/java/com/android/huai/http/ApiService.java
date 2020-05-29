package com.android.huai.http;

import com.android.huai.bean.KnowledgeSystem;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * http://www.wanandroid.com/
     * 获取只是体系数据
     */
    @GET("article/list/{page}/json")
    Observable<KnowledgeSystem> getKnowledgeSystem(@Path("page")int page, @Query("cid")int cid);
}
