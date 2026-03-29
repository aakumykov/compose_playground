package com.github.aakumykov.compose_playground.room

import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec

@RenameTable(fromTableName = "filters", toTableName = "filters_metadata")
class RenameDatabaseFiltersToFilterMetadata : AutoMigrationSpec