package com.selfi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.selfi.R
import kotlinx.android.synthetic.main.toolbar_activity.*

class TodolistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        toolbar_activity_title.setText("Todo List")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }
    }
}
