package com.topan.news.di

import com.topan.data.di.dataComponent
import com.topan.domain.di.domainComponent
import com.topan.presentation.di.presentationComponent

/**
 * Created by Topan E on 25/03/23.
 */
val appComponent = dataComponent + domainComponent + presentationComponent
