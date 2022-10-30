package com.blank.home.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blank.home.ui.dashboard.DashboardFragment
import com.blank.home.ui.dashboard.MapsFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DashboardFragment()
            1 -> fragment = MapsFragment()
        }
        return fragment as Fragment
    }
}