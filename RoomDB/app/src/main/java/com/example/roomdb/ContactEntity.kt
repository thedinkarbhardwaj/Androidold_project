package com.example.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
class ContactEntity {

    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    @ColumnInfo(name = "name")
    var name:String? = null

    @ColumnInfo(name = "phoneno")
    var phoneno:String?= null

    constructor(name: String?, phoneno: String?) {
        this.name = name
        this.phoneno = phoneno
    }
}