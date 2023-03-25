package com.topan.domain.usecases

import com.topan.domain.model.SourceList
import com.topan.domain.repository.NewsRepository
import com.topan.domain.utils.ResultCall

/**
 * Created by Topan E on 25/03/23.
 */
class GetSourceListUseCase(private val repository: NewsRepository) {
    suspend fun invoke(category: String): ResultCall<SourceList> {
        return repository.getSourceList(category)
    }
}
