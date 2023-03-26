package com.topan.data.repository

import com.topan.data.services.NewsServices
import com.topan.data.utils.call
import com.topan.domain.model.ArticleList
import com.topan.domain.model.SourceList
import com.topan.domain.repository.NewsRepository
import com.topan.domain.utils.ResultCall

/**
 * Created by Topan E on 25/03/23.
 */
class NewsRepositoryImpl(private val services: NewsServices): NewsRepository {
    override suspend fun getSourceList(category: String): ResultCall<SourceList> {
        return call { services.getSourceList(category = category) }
    }

    override suspend fun getArticleList(
        source: String,
        pageSize: Int,
        page: Int
    ): ResultCall<ArticleList> {
        return call { services.getArticleList(source = source, pageSize = pageSize, page = page) }
    }
}
