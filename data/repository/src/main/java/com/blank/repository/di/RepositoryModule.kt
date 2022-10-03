package com.blank.repository.di

import com.blank.domain.repository.AuthRepository
import com.blank.repository.AuthRepositoryImpl
import com.blank.repository.util.SharedPreferenceHelper
import org.koin.dsl.module

val repositoryModule = module {
    single { SharedPreferenceHelper(get()) }

    factory {
        AuthRepositoryImpl(get(),get()) as AuthRepository
    }
}
