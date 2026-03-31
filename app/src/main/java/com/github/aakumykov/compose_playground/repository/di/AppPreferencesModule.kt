package com.github.aakumykov.compose_playground.repository.di

import com.github.aakumykov.compose_playground.ui.filter_edit.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppPreferencesModule {

    @Provides
    fun provideAppPreferences(): AppPreferences {
        return AppPreferences
    }
}