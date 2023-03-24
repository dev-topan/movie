package com.topan.data.di

import com.topan.data.utils.OkHttpHelper
import com.topan.data.utils.RetrofitHelper
import org.koin.dsl.module

/**
 * Created by Topan E on 25/03/23.
 */
val networkModule = module {
    single { OkHttpHelper.create() }
    single { RetrofitHelper.create(get()) }
}
