package com.topan.data.services

import com.topan.data.BuildConfig
import com.topan.domain.model.SourceList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Topan E on 25/03/23.
 */
interface NewsServices {
    @GET("sources")
    suspend fun getSourceList(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = BuildConfig.LANGUAGE,
        @Query("category") category: String
    ) : Response<SourceList>
}
