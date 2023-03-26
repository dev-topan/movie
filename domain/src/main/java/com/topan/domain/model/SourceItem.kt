package com.topan.domain.model

import com.topan.domain.utils.emptyString

/**
 * Created by Topan E on 25/03/23.
 */
data class SourceItem(
    val id: String? = emptyString(),
    val name: String? = emptyString(),
    val description: String? = emptyString()
)
