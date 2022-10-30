package com.blank.local

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferenceModule = module {
    single { provideSettingsPreferences(androidContext()) }
    single { StoryDatabase.getInstance(androidContext()) }
}

private const val PREFERENCES_FILE_KEY = "com.blank.story"

private fun provideSettingsPreferences(app: Context): SharedPreferences =
    app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)