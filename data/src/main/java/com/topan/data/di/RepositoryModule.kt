package com.topan.data.di

import com.topan.data.repository.NewsRepositoryImpl
import com.topan.domain.repository.NewsRepository
import org.koin.dsl.module

/**
 * Created by Topan E on 25/03/23.
 */
val repositoryModule = module {
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}
