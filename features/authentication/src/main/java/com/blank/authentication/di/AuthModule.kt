package com.blank.authentication.di

import com.blank.authentication.ui.login.LoginViewModel
import com.blank.authentication.ui.register.RegisterViewModel
import org.koin.dsl.module


val authModule = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
}