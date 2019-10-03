package com.example.watchedonnetflix.RecyclerView_and_PageView


import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.watchedonnetflix.Database.Movie
import com.example.watchedonnetflix.Database.MyDatabase
import com.example.watchedonnetflix.R
import kotlinx.android.synthetic.main.fragment_show.*
import java.io.Serializable
import android.widget.LinearLayout
import android.support.constraint.ConstraintLayout


class ShowFragment() : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var database : MyDatabase
    private lateinit var ifWatched : String
    private lateinit var type : String
    private lateinit var movies: List<Movie>

    companion object {
        fun newInstance(type: String, ifWatched: String): ShowFragment {

            val fragment = ShowFragment()
            val args = Bundle()
            args.putString("type", type)
            args.putString("ifWatched", ifWatched)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myActivity = activity as FragmentActivity
        val context = activity?.applicationContext

        if (context != null) {
         type = arguments?.getString("type")!!
         ifWatched = arguments?.getString("ifWatched")!!

         database = Room.databaseBuilder(
                context,
                MyDatabase::class.java, "movie.db"
            ).build()

            AsyncTask.execute {
                if (type == "movies")
                    if (ifWatched == "watched")
                        movies = database.movieDao().showMovies(1)
                    else
                        movies = database.movieDao().showMovies(0)
                else if (type == "series")
                    if (ifWatched == "watched")
                        movies = database.movieDao().showSeries(1)
                    else
                        movies = database.movieDao().showSeries(0)
                else
                    if (ifWatched == "watched")
                        movies = database.movieDao().getAllWatched()
                    else
                        movies = database.movieDao().getAllToWatch()

                movies = movies.reversed()

                activity?.runOnUiThread {
                    linearLayoutManager = LinearLayoutManager(activity)
                    recyclerView.layoutManager = linearLayoutManager
                    recyclerView.itemAnimator = DefaultItemAnimator()
                    recyclerView.adapter = RecyclerAdapter(movies, context, myActivity)
                }

            }

        }
        return inflater.inflate(R.layout.fragment_show, container, false)
    }


}

