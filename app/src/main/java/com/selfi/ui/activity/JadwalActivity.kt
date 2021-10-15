package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.selfi.R
import com.selfi.adapter.JadwalHariRecyclerAdapter
import com.selfi.models.JadwalHari
import kotlinx.android.synthetic.main.activity_jadwal.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class JadwalActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        toolbar_activity_title.setText("Jadwal")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }

        val listJadwalHari: MutableList<JadwalHari> = ArrayList()
        listJadwalHari.add(JadwalHari("Senin"))
        listJadwalHari.add(JadwalHari("Selasa"))
        listJadwalHari.add(JadwalHari("Rabu"))
        listJadwalHari.add(JadwalHari("Kamis"))
        listJadwalHari.add(JadwalHari("Jum'at"))


        setRecyclerJadwal(listJadwalHari)
    }


    fun setRecyclerJadwal(listJadwalHari: List<JadwalHari>){

        rv_jadwal.setHasFixedSize(true)
        rv_jadwal.layoutManager = LinearLayoutManager(this@JadwalActivity)
        rv_jadwal.adapter =
            JadwalHariRecyclerAdapter(listJadwalHari,this)

    }
}
