package com.blank.authentication.di

import com.blank.authentication.ui.login.LoginViewModel
import com.blank.authentication.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val authModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}