package com.blank.story

import android.app.Application
import com.blank.authentication.di.authModule
import com.blank.home.di.homeModule
import com.blank.local.sharedPreferenceModule
import com.blank.remote.di.remoteModule
import com.blank.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    val modules = listOf(
        repositoryModule, sharedPreferenceModule, remoteModule(BuildConfig.API_BASE_URL),
        authModule, homeModule
    )

    override fun onCreate() {
        super.onCreate()
        val koinApp = KoinAndroidApplication.create(this, Level.ERROR)
            .modules(modules)
            .androidContext(this)
        startKoin(koinApp)

    }
}