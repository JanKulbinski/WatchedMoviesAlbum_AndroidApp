package com.example.watchedonnetflix.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
}