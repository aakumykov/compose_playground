package com.github.aakumykov.compose_playground.repository.di

import com.github.aakumykov.compose_playground.repository.DefaultFilterRepository
import com.github.aakumykov.compose_playground.repository.FilterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsFilterRepository(
        defaultFilterRepository: DefaultFilterRepository
    ): FilterRepository
}