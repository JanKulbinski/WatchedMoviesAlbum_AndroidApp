package com.example.watchedonnetflix

import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.watchedonnetflix.Database.Movie
import com.example.watchedonnetflix.Database.MyDatabase
import com.example.watchedonnetflix.RecyclerView_and_PageView.ShowFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_moviedetails.*

class MovieDetailsActivity: AppCompatActivity() {

    lateinit var  movie : Movie
    lateinit var  database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moviedetails)

        loadingPanel.visibility = View.VISIBLE

        val title = intent.getStringExtra("title")

        database = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java, "movie.db"
        ).build()

        AsyncTask.execute {

            movie = database.movieDao().search(title)

            runOnUiThread {
                titleTextView.text = movie.Title
                runtimeTextView.text = movie.Runtime
                yearTextView.text = movie.Year
                genreTextView.text = movie.Genre
                countryTextView.text = movie.Country
                plotTextView.text = movie.Plot
                ratingTextView.text = getString(R.string.rating, movie.imdbRating)

                if(movie.Poster == "N/A")
                    Picasso.get().load(R.drawable.question_mark).resize(330,350).into(imageView)
                else
                    Picasso.get().load(movie.Poster).into(imageView)
                loadingPanel.visibility = View.GONE }

            if(movie.ifWatched) {
                button.isClickable = false
                button.visibility = View.GONE
            }
        }

    }

    fun markAsWatched(view: View) {
        movie.ifWatched = true
        AsyncTask.execute {
            database.movieDao().update(movie)
        }
        Toast.makeText(this@MovieDetailsActivity, "You've marked " + movie.Title + " as watched", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun deleteFromList(view : View) {
        AsyncTask.execute {
            database.movieDao().delete(movie)
        }
        Toast.makeText(this@MovieDetailsActivity, "You've deleted " + movie.Title + " from list", Toast.LENGTH_SHORT).show()
        finish()
    }
}