package com.selfi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.JadwalHari
import com.selfi.models.JadwalMapel
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.JadwalService
import com.selfi.services.api.ServiceBuilder
import retrofit2.Call

class JadwalHariRecyclerAdapter(
    private val listHari: List<JadwalHari>,
    private val mContext: Context
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


        fun setChildJadwal(recyclerView: RecyclerView, mValues: List<JadwalMapel>) {
            val pref = SharedPrefHelper.getInstance(mContext).getAccount().Idkelas
            val service = ServiceBuilder.buildService(JadwalService::class.java)
                .getJadwalByKelasByHari(pref, item.hari)

           // service.enqueue(object : Call<>)
        }
    }

    class ListViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        var txt_hari: TextView = itemVIew.findViewById(R.id.txt_hari_jadwal)
        var rv_child: RecyclerView = itemVIew.findViewById(R.id.rv_child_jadwal)
    }



}