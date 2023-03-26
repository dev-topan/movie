package com.topan.domain.model

import com.topan.domain.utils.emptyInt
import com.topan.domain.utils.emptyString

/**
 * Created by Topan E on 26/03/23.
 */
data class ArticleList(
    val status: String? = emptyString(),
    val totalResults: Int? = emptyInt(),
    val articles: List<ArticleItem>? = emptyList()
)
