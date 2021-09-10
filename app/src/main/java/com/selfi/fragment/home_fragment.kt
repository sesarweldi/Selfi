package com.selfi.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.selfi.*
import com.selfi.activity.*
import kotlinx.android.synthetic.main.fragment_home_fragment.view.*



class home_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)

        //val cardBelajar:CardView = findViewById(R.id.card_main_belajar)

        view.card_main_belajar.setOnClickListener{
            startActivity(Intent(activity, BelajarActivity::class.java))
        }

        view.card_main_jadwal.setOnClickListener{
            startActivity(Intent(activity, JadwalActivity::class.java))
        }

        view.card_main_konsul.setOnClickListener{
            startActivity(Intent(activity, KonsulActivity::class.java))
        }

        view.card_main_motivasi.setOnClickListener{
            startActivity(Intent(activity, MotivasiActivity::class.java))
        }

        view.card_main_target.setOnClickListener{
            startActivity(Intent(activity, TargetActivity::class.java))
        }

        view.card_main_todo.setOnClickListener{
            startActivity(Intent(activity, TodolistActivity::class.java))
        }

        return view
    }
}
