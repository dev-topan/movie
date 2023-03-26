package com.topan.domain.model

import com.topan.domain.utils.emptyString

/**
 * Created by Topan E on 26/03/23.
 */
data class ArticleItem(
    val source: SourceItem? = SourceItem(),
    val title: String? = emptyString(),
    val urlToImage: String? = emptyString(),
    val publishedAt: String? = emptyString(),
    val url: String? = emptyString()
)
