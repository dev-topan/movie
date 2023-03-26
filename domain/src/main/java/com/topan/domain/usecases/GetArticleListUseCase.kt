package com.topan.domain.usecases

import com.topan.domain.model.ArticleList
import com.topan.domain.repository.NewsRepository
import com.topan.domain.utils.ResultCall

/**
 * Created by Topan E on 26/03/23.
 */
class GetArticleListUseCase(private val repository: NewsRepository) {
    suspend fun invoke(source: String, pageSize: Int, page: Int): ResultCall<ArticleList> {
        return repository.getArticleList(source = source, pageSize = pageSize, page = page)
    }
}
