package com.selfi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.JadwalHari
import com.selfi.models.JadwalMapel
import com.selfi.services.api.JadwalService
import com.selfi.services.api.ServiceBuilder
import kotlinx.android.synthetic.main.item_jadwal.*

class JadwalMapelRecyclerAdapter(private val mValues: List<JadwalMapel>, private val mContext:Context) :
    RecyclerView.Adapter<JadwalMapelRecyclerAdapter.listViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = listViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_target_child, parent, false)
    )

    override fun getItemCount(): Int {
    return mValues.size
    }

    override fun onBindViewHolder(
        holder: JadwalMapelRecyclerAdapter.listViewHolder,
        position: Int
    ) {
        val item = mValues[position]
        holder.txt_mapel.text = item.nama_mapel
    }

    class listViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_mapel: TextView = itemView.findViewById(R.id.txt_Jadwal_Mapel)
    }

}