package com.selfi.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.selfi.R
import com.selfi.models.Belajar

class BelajarNoraRecyclerAdapter(
    private val mValues: List<Belajar>,
    private val mContext: Context
) : RecyclerView.Adapter<BelajarNoraRecyclerAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_belajar, parent, false)
    )

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = mValues[position]
        holder.txtJudul.text = item.judul
        holder.txtPenulis.text = item.penulis
        holder.txtPenerbit.text = item.penerbit

        Glide.with(holder.itemView.context)
            .load("https://selfi.laam.my.id/sampulBuku/" + item.sampul)
            .into(holder.gambar)


        holder.cardBelajar.setOnClickListener { view->
            val i = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://selfi.laam.my.id/lampiran/" + item.lampiran))
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(i)
        }
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gambar: ImageView = itemView.findViewById(R.id.img_cover_buku)
        var txtJudul: TextView = itemView.findViewById(R.id.judul_buku)
        var txtPenulis: TextView = itemView.findViewById(R.id.penulis_buku)
        var txtPenerbit: TextView = itemView.findViewById(R.id.penerbit_buku)
        var cardBelajar: CardView = itemView.findViewById(R.id.card_item_belajar)
    }
}