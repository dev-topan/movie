package com.topan.domain.di

import com.topan.domain.usecases.GetArticleListUseCase
import com.topan.domain.usecases.GetSourceListUseCase
import org.koin.dsl.module

/**
 * Created by Topan E on 25/03/23.
 */
val useCaseModule = module {
    single { GetSourceListUseCase(get()) }
    single { GetArticleListUseCase(get()) }
}
