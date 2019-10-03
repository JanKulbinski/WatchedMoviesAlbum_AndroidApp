package com.example.watchedonnetflix.Database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter


@Entity(tableName = "movies")
data class Movie(

    @PrimaryKey
    @ColumnInfo(name = "Title") var Title : String,

    @ColumnInfo(name = "Year") var Year : String,
    @ColumnInfo(name = "Runtime") var Runtime : String,
    @ColumnInfo(name = "Genre") var Genre : String,
    @ColumnInfo(name = "Country") var Country : String,
    @ColumnInfo(name = "Poster") var Poster : String,
    @ColumnInfo(name = "Plot") var Plot : String,
    @ColumnInfo(name = "imdbRating") var imdbRating : Float,
    @ColumnInfo(name = "ifWatched") var ifWatched : Boolean,
    @ColumnInfo(name = "type") var Type : String
)