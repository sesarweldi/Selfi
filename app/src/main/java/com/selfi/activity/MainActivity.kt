package com.selfi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.selfi.R
import com.selfi.fragment.home_fragment
import com.selfi.fragment.notifFragment
import com.selfi.fragment.profile_fragment

class MainActivity : AppCompatActivity() {

    lateinit var chipNavigationBar:ChipNavigationBar
    lateinit var constraint: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //    val window= window
      //  window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        constraint = findViewById(R.id.coordinator)

        chipNavigationBar=findViewById(R.id.chip_app_bar)
      //  chipNavigationBar.setItemSelected(R.id.notif,true)
        supportFragmentManager.beginTransaction().replace(R.id.root_layout,home_fragment()).commit()

        bottom_menu()
    }


    private fun bottom_menu(){
        chipNavigationBar.setOnItemSelectedListener({
            when(it){
                R.id.notif ->{
                    supportFragmentManager.beginTransaction().replace(R.id.root_layout, notifFragment()).commit()
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.root_layout,profile_fragment()).commit()
                }
            }
        })
    }

     fun floating_button(view: View){
        supportFragmentManager.beginTransaction().replace(R.id.root_layout,home_fragment()).commit()
         chipNavigationBar.setItemSelected(R.id.notif,false)
         chipNavigationBar.setItemSelected(R.id.profile,false)
    }
}

