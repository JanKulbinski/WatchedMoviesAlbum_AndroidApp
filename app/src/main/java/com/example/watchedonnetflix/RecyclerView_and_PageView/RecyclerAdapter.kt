package com.example.watchedonnetflix.RecyclerView_and_PageView

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.watchedonnetflix.AddActivity
import com.example.watchedonnetflix.Database.Movie
import com.example.watchedonnetflix.MovieDetailsActivity
import com.example.watchedonnetflix.R
import com.squareup.picasso.Picasso

class RecyclerAdapter(private val movies: List<Movie>, val context : Context, val activity : FragmentActivity) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val REQUESTCODE = 111

        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var textTitle = itemView.findViewById<TextView>(R.id.textTitle)
        var textYear = itemView.findViewById<TextView>(R.id.textYear)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("title",movies[position].Title)
            startActivityForResult(activity,intent,REQUESTCODE,null)
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(movies[position].Poster).into(holder.imageView)
        holder.textTitle.text = movies[position].Title
        holder.textYear.text = movies[position].Year
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(v)
    }


}