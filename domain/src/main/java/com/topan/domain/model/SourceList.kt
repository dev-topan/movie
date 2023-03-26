package com.topan.domain.model

import com.topan.domain.utils.emptyString

/**
 * Created by Topan E on 25/03/23.
 */
data class SourceList(
    val status: String? = emptyString(),
    val sources: List<SourceItem>? = emptyList()
)
