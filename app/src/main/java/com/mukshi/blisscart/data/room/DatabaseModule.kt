package com.mukshi.blisscart.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "app_database"
        ).build()
    }
  //  private var database: Database? = null


    @Provides
    fun provideCartDao(database: Database): CartDao {
        return database.cartDao()
    }

//
//    fun getDatabase(): Database {
//        return database ?: throw IllegalStateException("Database not initialized")
//    }
}