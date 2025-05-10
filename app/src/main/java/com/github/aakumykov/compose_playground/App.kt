package com.github.aakumykov.compose_playground;

import android.app.Application;
import androidx.room.Room
import com.github.aakumykov.compose_playground.repository.room.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        _appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    companion object {
        private var _appDatabase: AppDatabase? = null
        val appDatabase: AppDatabase get() = _appDatabase!!
    }
}

val appDatabase get() = App.appDatabase