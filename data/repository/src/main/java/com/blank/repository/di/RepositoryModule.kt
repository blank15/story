package com.blank.repository.di

import com.blank.domain.repository.AuthRepository
import com.blank.domain.repository.StoriesRepository
import com.blank.repository.AuthRepositoryImpl
import com.blank.repository.StoriesRepositoryImpl
import com.blank.repository.util.SharedPreferenceHelper
import org.koin.dsl.module

val repositoryModule = module {
    single { SharedPreferenceHelper(get()) }

    factory<AuthRepository> {
        AuthRepositoryImpl(get(), get())
    }
    factory<StoriesRepository> { StoriesRepositoryImpl(get(), get(), get()) }
}

