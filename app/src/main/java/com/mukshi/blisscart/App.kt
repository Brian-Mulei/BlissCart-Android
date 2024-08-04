package com.mukshi.blisscart

import android.app.Application
import androidx.room.Room
import com.mukshi.blisscart.data.room.Database
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){

    companion object {
        lateinit var instance: App
            private set
    }

    private var database: Database? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "app_database"
        ).build()
    }

    fun getDatabase(): Database {
        return database ?: throw IllegalStateException("Database not initialized")
    }
}