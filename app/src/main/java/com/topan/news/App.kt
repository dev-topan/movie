package com.topan.news

import android.app.Application
import com.topan.news.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Topan E on 24/03/23.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        onStartKoin()
    }

    private fun onStartKoin() = startKoin {
        androidContext(this@App)
        modules(appComponent)
    }
}
