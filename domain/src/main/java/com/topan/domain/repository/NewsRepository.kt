package com.topan.domain.repository

import com.topan.domain.model.ArticleList
import com.topan.domain.model.SourceList
import com.topan.domain.utils.ResultCall

/**
 * Created by Topan E on 25/03/23.
 */
interface NewsRepository {
    suspend fun getSourceList(category: String): ResultCall<SourceList>
    suspend fun getArticleList(
        source: String,
        query: String,
        pageSize: Int,
        page: Int
    ): ResultCall<ArticleList>
}
