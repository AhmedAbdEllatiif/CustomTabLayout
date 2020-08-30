package com.ahmed.library

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    var fragments = ArrayList<Fragment?>()

    fun addAllFragments(fragments: ArrayList<Fragment?>) {
        this.fragments = fragments
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]!!
    }

    override fun getCount(): Int {
        return fragments.size
    }
}