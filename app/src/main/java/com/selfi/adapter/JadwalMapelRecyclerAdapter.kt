package com.selfi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.JadwalHari

class JadwalMapelRecyclerAdapter(private val mValues: List<JadwalHari.JadwalMapel>, private val mContext:Context) :
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
        var txt_mapel: TextView = itemView.findViewById(R.id.text_mapelJadwal)
    }

}