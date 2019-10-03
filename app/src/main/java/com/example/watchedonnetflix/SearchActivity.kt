package com.example.watchedonnetflix

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    fun search(view: View) {
        val type : String

        if(buttonMovie.isChecked)
            type = "movie"
        else
            type = "series"

        val requestCode = 123
        val intent = Intent(this@SearchActivity, AddActivity::class.java)
        intent.putExtra("phrase", editText.text.toString())
        intent.putExtra("type",type)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            if (data?.data.toString().equals("no"))
                finish()
        }
    }
}
