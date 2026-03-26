package com.github.aakumykov.compose_playground.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.aakumykov.compose_playground.data.model.Filter

@Database(
    entities = [Filter::class ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFilterDAO(): FilterDAO
}