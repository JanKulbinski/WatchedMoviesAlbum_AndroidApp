package com.example.watchedonnetflix

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.watchedonnetflix.RecyclerView_and_PageView.MyPagerAdapter
import com.example.watchedonnetflix.RecyclerView_and_PageView.ShowFragment
import kotlinx.android.synthetic.main.activity_showlist.*
import java.lang.ref.WeakReference

class ShowListActivity : AppCompatActivity() {

    private var fragmentsList: MutableList<WeakReference<Fragment>> = ArrayList<WeakReference<Fragment>>()


    override fun onAttachFragment(fragment: Fragment?) {
        if(fragment != null)
            fragmentsList.add(WeakReference(fragment))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showlist)

        val type = intent.getStringExtra("watchType")
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager, type)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

        if (type.equals("watched"))
            titleList.text = "Watched"
        else
            titleList.text = "To watch"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val fragments = getActiveFragments()
        fragments.forEach {
            it.onCreateView(LayoutInflater.from(this),null,null)
        }
    }

    fun getActiveFragments(): List<Fragment> {
        val result = ArrayList<Fragment>()
        fragmentsList.forEach{
            val f = it.get()
            if (f != null && f.isVisible)
                    result.add(f)

        }
        return result
    }

}