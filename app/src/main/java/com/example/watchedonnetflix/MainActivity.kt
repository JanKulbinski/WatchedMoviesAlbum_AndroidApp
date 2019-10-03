package com.example.watchedonnetflix


import android.arch.persistence.room.Room
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.watchedonnetflix.Database.Movie
import com.example.watchedonnetflix.Database.MyDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun adding(view: View) {
        val intent = Intent(this@MainActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    fun show(view: View) {
        val intent = Intent(this@MainActivity, ShowListActivity::class.java)
        if(view.id == buttonToWatch.id)
            intent.putExtra("watchType", "toWatch")
        else
            intent.putExtra("watchType","watched")

        startActivity(intent)
    }

}