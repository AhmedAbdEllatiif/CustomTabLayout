package com.ahmed.library

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList


class SlidePagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private var fragments = ArrayList<Fragment?>()

    fun addAllFragments(fragments: ArrayList<Fragment?>?) {
        this.fragments = fragments!!
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment = fragments[position]!!
}