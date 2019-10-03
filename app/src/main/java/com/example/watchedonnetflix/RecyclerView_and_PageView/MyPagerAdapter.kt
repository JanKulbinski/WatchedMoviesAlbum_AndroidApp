package com.example.watchedonnetflix.RecyclerView_and_PageView

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter(fragmentManager: FragmentManager, var ifWatched : String) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment {
        return when(p0) {
            0 -> ShowFragment.newInstance("all", ifWatched)
            1 -> ShowFragment.newInstance("movies", ifWatched)
            else -> ShowFragment.newInstance("series", ifWatched)
        }
    }

    override fun getCount() : Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "All"
            1 -> "Movies"
            else -> "Series"
        }
    }
}