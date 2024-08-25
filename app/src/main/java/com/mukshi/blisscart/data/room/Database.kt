package com.mukshi.blisscart.data.room

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.mukshi.blisscart.data.model.CartItem

@Database(entities = [CartItem::class], version = 2)

abstract class Database:RoomDatabase() {

    abstract fun cartDao(): CartDao


}
