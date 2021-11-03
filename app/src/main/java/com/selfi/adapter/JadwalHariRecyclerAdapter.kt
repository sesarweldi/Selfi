package com.selfi.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.JadwalHari
import com.selfi.models.response.DataResponseModel
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.JadwalService
import com.selfi.services.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalHariRecyclerAdapter(
    private val listHari: List<JadwalHari>,
    private val mContext: Context
   /* private val listMapel: List<JadwalHari.JadwalMapel>*/
) :
    RecyclerView.Adapter<JadwalHariRecyclerAdapter.ListViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JadwalHariRecyclerAdapter.ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal, parent, false)
    )

    override fun getItemCount(): Int {
        return listHari.size
    }

    override fun onBindViewHolder(holder: JadwalHariRecyclerAdapter.ListViewHolder, position: Int) {
        val item = listHari[position]
        holder.txt_hari.text = item.hari
        setChildJadwall(holder.rv_child, item.mapel)
    }

    class ListViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        var txt_hari: TextView = itemVIew.findViewById(R.id.txt_hari_jadwal)
        var rv_child: RecyclerView = itemVIew.findViewById(R.id.rv_child_jadwal)
    }


    fun setChildJadwall(recyclerView: RecyclerView, listMapel: List<JadwalHari.JadwalMapel>){
        val childRecyclerAdapter = JadwalMapelRecyclerAdapter(listMapel, mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = childRecyclerAdapter
    }



}