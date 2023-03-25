package com.topan.data.di

import com.topan.data.services.NewsServices
import com.topan.data.utils.ServiceHelper
import org.koin.dsl.module

/**
 * Created by Topan E on 25/03/23.
 */
val serviceModule = module {
    factory { ServiceHelper.create<NewsServices>(get()) }
}
