package com.example.watchedonnetflix

import android.arch.persistence.room.Room
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.watchedonnetflix.Database.Movie
import com.example.watchedonnetflix.Database.MyDatabase
import com.example.watchedonnetflix.ImdbAPI.OMDbAPI
import com.example.watchedonnetflix.ImdbAPI.OMDbDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddActivity : AppCompatActivity() {

    companion object {
        const val myAPIkey = "35c93c49"
    }

    var imageURl = ""
    var rating = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        loadingPanel.visibility = View.VISIBLE
        button.isClickable = false
        button2.isClickable = false
        loadFromImdbAPI()
    }

    fun loadFromImdbAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val omdb = retrofit.create(OMDbAPI::class.java)

        val call = omdb.search(intent.getStringExtra("phrase"), myAPIkey, "short")

        call.enqueue( object : Callback<OMDbDTO> {
            override fun onFailure(call: Call<OMDbDTO>, t: Throwable) {
                Log.d("WMA", "fail")
            }

            override fun onResponse(call: Call<OMDbDTO>, response: Response<OMDbDTO>) {
                val body = response.body()

                if(body?.Title.toString().equals("null")) {

                    val data = Intent()

                    val builder = AlertDialog.Builder(this@AddActivity)
                    builder.setTitle("Ups!")
                    builder.setMessage("Your search did not match any results. Do you want to try again?")

                    builder.setPositiveButton("Yes", DialogInterface.OnClickListener {
                            dialog, id -> data.setData(Uri.parse("yes")); setResult(RESULT_OK, data); finish()
                    })

                    builder.setNegativeButton("No", DialogInterface.OnClickListener {
                            dialog, id -> data.setData(Uri.parse("no")); setResult(RESULT_OK, data); finish()
                    })

                    builder.show()
                } else {
                    imageURl = body?.Poster.toString()
                    if (body?.imdbRating != null)
                        rating = body.imdbRating


                    if(body?.Poster == "N/A")
                        Picasso.get().load(R.drawable.question_mark).resize(330,350).into(imageView)
                    else
                        Picasso.get().load(body?.Poster).into(imageView)

                    titleTextView.text = body?.Title
                    runtimeTextView.text = body?.Runtime
                    yearTextView.text = body?.Year
                    genreTextView.text = body?.Genre
                    countryTextView.text = body?.Country
                    ratingTextView.text = getString(R.string.rating, body?.imdbRating)
                    plotTextView.text = body?.Plot

                    searchInDB(body?.Title.toString())
                    button.isClickable = true
                    button2.isClickable = true
                }
                loadingPanel.visibility = View.GONE
            }

        })

    }

    fun addToWatch(view : View) = addToDB(false)

    fun addWatched(view : View) = addToDB(true)

    fun addToDB(ifWatched : Boolean) {

        val database = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java, "movie.db"
        ).build()

        val title = titleTextView.text.toString()
        val runTime = runtimeTextView.text.toString()
        val year = yearTextView.text.toString()
        val genre = genreTextView.text.toString()
        val country = countryTextView.text.toString()
        val plot = plotTextView.text.toString()

        AsyncTask.execute {
            database.movieDao().insert(
                Movie(
                    title,
                    year,
                    runTime,
                    genre,
                    country,
                    imageURl,
                    plot,
                    rating,
                    ifWatched,
                    intent.getStringExtra("type")
                )
            )
        }

        if (ifWatched) {
            button2.setTextColor(getColor(R.color.colorRed))
            button.setTextColor(getColor(R.color.colorWhite))
        } else {
            button.setTextColor(getColor(R.color.colorRed))
            button2.setTextColor(getColor(R.color.colorWhite))
        }

        val data = Intent()
        val builder = AlertDialog.Builder(this@AddActivity)
        builder.setTitle("Success!")
        builder.setMessage("You've added new record! Do you want go back to menu?")
        builder.setNegativeButton("No", DialogInterface.OnClickListener {
                dialog, id ->
        })

        builder.setPositiveButton("Yes", DialogInterface.OnClickListener {
                dialog, id -> data.setData(Uri.parse("no")); setResult(RESULT_OK, data); finish()
        })
        builder.show()
    }

    fun searchInDB(title : String) {

        val database = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java, "movie.db"
        ).build()

        AsyncTask.execute {
            val movie = database.movieDao().search(title)
            runOnUiThread {
                if (movie?.Title == title)
                    if (movie.ifWatched)
                        button2.setTextColor(getColor(R.color.colorRed))
                    else
                        button.setTextColor(getColor(R.color.colorRed))

            }
        }
    }
}
