package com.github.aakumykov.compose_playground.room.di

import android.content.Context
import androidx.room.Room
import com.github.aakumykov.compose_playground.room.AppDatabase
import com.github.aakumykov.compose_playground.room.FilterDAO
import com.github.aakumykov.compose_playground.room.FilterMetadataDAO
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
    fun provideFilterMetadataDAO(appDatabase: AppDatabase): FilterMetadataDAO {
        return appDatabase.getFilterMetadataDAO()
    }

    @Provides
    fun provideFilterDAO(appDatabase: AppDatabase): FilterDAO {
        return appDatabase.getFilterDAO()
    }

    @Provides
    fun provideRuleDAO(appDatabase: AppDatabase): RuleDAO {
        return appDatabase.getRuleDAO()
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