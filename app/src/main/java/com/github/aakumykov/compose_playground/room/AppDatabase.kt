package com.github.aakumykov.compose_playground.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.github.aakumykov.compose_playground.model.FilterMetadata
import java.lang.ProcessBuilder.Redirect.to

@Database(
    entities = [FilterMetadata::class ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = RenameDatabaseFiltersToFilterMetadata::class)
    ],
    version = 2,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFilterDAO(): FilterDAO
}