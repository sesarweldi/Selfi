package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.selfi.R
import com.selfi.ui.fragment.home_fragment
import com.selfi.ui.fragment.profile_fragment

class MainActivity : AppCompatActivity() {

    lateinit var chipNavigationBar:ChipNavigationBar
    lateinit var constraint: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        constraint = findViewById(R.id.coordinator)

        chipNavigationBar=findViewById(R.id.chip_app_bar)
        chipNavigationBar.setItemSelected(R.id.fragmentHome,true)
        supportFragmentManager.beginTransaction().replace(R.id.root_layout,home_fragment()).commit()

        bottom_menu()
    }


    private fun bottom_menu(){
        chipNavigationBar.setOnItemSelectedListener({
            when(it){
                R.id.fragmentHome ->{
                    supportFragmentManager.beginTransaction().replace(R.id.root_layout, home_fragment()).commit()
                }

                R.id.fragmentProfile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.root_layout,profile_fragment()).commit()
                }
            }
        })
    }

  /*   fun floating_button(view: View){
        supportFragmentManager.beginTransaction().replace(R.id.root_layout,home_fragment()).commit()
         chipNavigationBar.setItemSelected(R.id.notif,false)
         chipNavigationBar.setItemSelected(R.id.profile,false)
    }*/
}

