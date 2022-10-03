package com.blank.home.di

import com.blank.home.ui.addstory.AddStoryViewModel
import com.blank.home.ui.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { AddStoryViewModel(get()) }
}