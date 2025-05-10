package com.github.aakumykov.compose_playground.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookData
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookMetadata

@Database(
    entities = [
        RoomBookMetadata::class,
        RoomBookData::class,
   ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {


}