package com.topan.data.services

import com.topan.data.BuildConfig
import com.topan.domain.model.ArticleList
import com.topan.domain.model.SourceList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Topan E on 25/03/23.
 */
interface NewsServices {
    @GET("top-headlines/sources")
    suspend fun getSourceList(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = BuildConfig.LANGUAGE,
        @Query("category") category: String
    ) : Response<SourceList>

    @GET("top-headlines")
    suspend fun getArticleList(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = BuildConfig.LANGUAGE,
        @Query("sources") source: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Response<ArticleList>
}
