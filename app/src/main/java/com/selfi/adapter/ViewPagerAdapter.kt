package com.selfi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.selfi.fragment.BelajarNoraFragment
import com.selfi.fragment.BelajarProduktifFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0 -> {
                BelajarNoraFragment()
            }

            1-> {
                BelajarProduktifFragment()
            }

            else -> {
                Fragment()
            }
        }
    }
}