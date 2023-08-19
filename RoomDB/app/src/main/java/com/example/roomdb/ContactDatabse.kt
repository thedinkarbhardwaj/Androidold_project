package com.example.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactDatabse:RoomDatabase() {
    abstract fun contactDaoo():ContactDao

}