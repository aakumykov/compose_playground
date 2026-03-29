package com.github.aakumykov.compose_playground.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.room.di.RuleDAO
import java.lang.ProcessBuilder.Redirect.to

@Database(
    entities = [FilterMetadata::class, Rule::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = RenameDatabaseFiltersToFilterMetadata::class),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
    ],
    version = 4,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFilterDAO(): FilterDAO
    abstract fun getFilterMetadataDAO(): FilterMetadataDAO
    abstract fun getRuleDAO(): RuleDAO
}