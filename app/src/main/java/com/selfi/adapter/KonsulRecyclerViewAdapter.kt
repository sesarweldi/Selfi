package com.selfi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R

class KonsulRecyclerViewAdapter : RecyclerView.Adapter<KonsulRecyclerViewAdapter.ListViewHolder>() {


    private var nama = arrayOf("Tri", "Dede", "Kuri", "Syamsul","Sevia")
    private var pelajaran = arrayOf("Sistem keamanan jaringan","Software as a Service","Infrastruktur as a Service" ,"Platform as a Service", "SIoT")
    private var gambarGuru= intArrayOf(R.drawable.logo, R.drawable.logo, R.drawable.logo, R.drawable.logo, R.drawable.logo)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.item_konsul, parent, false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int {
       return nama.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.text_namaGuru.text = nama[position]
        holder.text_mapel.text = pelajaran[position]
        holder.img_guru.setImageResource(gambarGuru[position])
    }

    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text_namaGuru: TextView = itemView.findViewById(R.id.text_namaGuru)
        var text_mapel: TextView = itemView.findViewById(R.id.text_mapel)
        var img_guru:ImageView = itemView.findViewById(R.id.img_konsul)

    }
}