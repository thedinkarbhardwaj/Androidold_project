package com.example.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {

    @Insert
    fun insert(contactEntity: ContactEntity)

    @Query("select * from contact")
    fun getAllContact():List<ContactEntity>

}