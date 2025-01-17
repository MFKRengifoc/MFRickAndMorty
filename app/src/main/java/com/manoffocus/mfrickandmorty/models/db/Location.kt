package com.manoffocus.mfrickandmorty.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "location_tbl")
@TypeConverters(DateConverter::class)
data class Location(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "location_id")
    val locationId : Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String
)