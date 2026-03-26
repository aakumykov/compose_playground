package com.github.aakumykov.compose_playground.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    @Named("qwerty")
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}

