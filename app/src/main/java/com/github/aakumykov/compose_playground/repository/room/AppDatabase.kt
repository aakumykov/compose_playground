package com.github.aakumykov.compose_playground.repository.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.aakumykov.compose_playground.repository.room.dao.BookDAO
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookData
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookMetadata

@Database(
    entities = [
        RoomBookMetadata::class,
        RoomBookData::class,
   ],
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2),
//    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBookDAO(): BookDAO

}