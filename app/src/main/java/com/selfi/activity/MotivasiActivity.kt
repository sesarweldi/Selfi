package com.selfi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.selfi.R
import kotlinx.android.synthetic.main.toolbar_activity.*

class MotivasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motivasi)
        toolbar_activity_title.setText("Motivasi")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }
    }
}
