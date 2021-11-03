package com.selfi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.JadwalMapelProfile

class JadwalByHariRecyclerAdapter(private val mValues: List<JadwalMapelProfile>, private val mContext: Context) :
    RecyclerView.Adapter<JadwalByHariRecyclerAdapter.viewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = viewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal_profile, parent, false)
    )

    override fun getItemCount(): Int {
       return mValues.size
    }

    override fun onBindViewHolder(holder: JadwalByHariRecyclerAdapter.viewHolder, position: Int) {
        val item = mValues[position]
        holder.txt_mapel.text = item.mapel.nama_mapel
    }

    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        var txt_mapel: TextView = itemView.findViewById(R.id.text_mapelJadwalProfile)
    }
}