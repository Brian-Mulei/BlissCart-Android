package com.mukshi.blisscart.data.room

import androidx.room.RoomDatabase
import androidx.room.Database
import com.mukshi.blisscart.data.model.CartItem

@Database(entities = [CartItem::class], version = 1)

abstract class Database:RoomDatabase() {

    abstract fun cartDao(): CartDao

}