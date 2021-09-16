package com.selfi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.selfi.R
import com.selfi.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_belajar.view.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class BelajarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belajar)

        //Toolbar
        toolbar_activity_title.setText("Belajar")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }

        //ViewPager
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2= findViewById<ViewPager2>(R.id.view_pager_2)
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout,viewPager2){tab, position ->
            when(position){
                0 -> tab.text="Nora"
                1 -> tab.text="Produktif"
            }
        }.attach()



    }
}
