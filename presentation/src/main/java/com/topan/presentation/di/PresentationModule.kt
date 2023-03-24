package com.topan.presentation.di

import com.topan.presentation.features.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Topan E on 25/03/23.
 */
val viewModelModule = module {
    viewModel { HomeViewModel() }
}
