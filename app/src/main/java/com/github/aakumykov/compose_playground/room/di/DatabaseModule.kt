package com.github.aakumykov.compose_playground.room.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.aakumykov.compose_playground.room.AppDatabase
import com.github.aakumykov.compose_playground.room.FilterDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideFilterDAO(appDatabase: AppDatabase): FilterDAO {
        return appDatabase.getFilterDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}