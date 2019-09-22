package com.oleksandr.demsky.weatherapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class WeatherPagerAdapter(fm: FragmentManager, private val titles: ArrayList<String>) : FragmentStatePagerAdapter(fm) {
    private val fragments = ArrayList<Fragment>()

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    override fun getItem(position: Int): Fragment =
        fragments[position]


    override fun getCount(): Int = fragments.size

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}