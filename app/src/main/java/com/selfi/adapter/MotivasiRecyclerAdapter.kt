package com.selfi.adapter

import android.content.Context
import android.content.Intent
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
import com.selfi.models.Motivasi
import com.selfi.ui.activity.MotivasiDetailActivity

class MotivasiRecyclerAdapter(private val mValues: List<Motivasi>, private val mContext: Context):
    RecyclerView.Adapter<MotivasiRecyclerAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_motivasi,parent, false)
    )

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = mValues[position]
        holder.text_judulMotivasi.text = item.judul

        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(7))
        Glide.with(holder.itemView.context)
            .load("https://selfi.laam.my.id/sampul/" + item.sampul)
            .apply(requestOptions)
            .into(holder.img_bannerMotivasi)

        holder.card_motivasi.setOnClickListener { view->
            val intent = Intent(mContext.applicationContext,MotivasiDetailActivity::class.java)
            intent.putExtra("deskripsi",item.deskripsi)
            intent.putExtra("gambar","https://selfi.laam.my.id/sampul/" + item.sampul)
            intent.putExtra("judul",item.judul)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(intent)
        }
    }


    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var text_judulMotivasi: TextView = itemView.findViewById(R.id.txt_judulMotivasi)
        var img_bannerMotivasi: ImageView = itemView.findViewById(R.id.img_banner_motivasi)
        var card_motivasi: CardView = itemView.findViewById(R.id.card_motivasi)
    }

}