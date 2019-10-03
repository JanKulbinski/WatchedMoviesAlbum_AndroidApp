package com.example.watchedonnetflix.Database

import android.arch.persistence.room.*
import com.example.watchedonnetflix.Database.Movie

@Dao
interface MovieDAO {

    @Query("select * from movies where ifWatched = :ifWatched and type = 'movie'")
    fun showMovies(ifWatched : Int) : List<Movie>

    @Query("select * from movies where ifWatched = :ifWatched and type = 'series'")
    fun showSeries(ifWatched : Int) : List<Movie>

    @Query("select * from movies where ifWatched = 1")
    fun getAllWatched() : List<Movie>

    @Query("select * from movies where ifWatched = 0")
    fun getAllToWatch() : List<Movie>

    @Query("select * from movies where Title = :title")
    fun search(title : String) : Movie

    @Query("delete from movies where Title = :title")
    fun deleteByTitle(title : String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie : Movie)

    @Delete
    fun delete(movie : Movie)

    @Update
    fun update(movie : Movie)
}