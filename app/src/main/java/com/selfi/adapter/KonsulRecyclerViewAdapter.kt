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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.konsul
import kotlinx.android.synthetic.main.item_konsul.view.*

class KonsulRecyclerViewAdapter(private val mValues:List<konsul>, private val mContext: Context) : RecyclerView.Adapter<KonsulRecyclerViewAdapter.ListViewHolder>() {



  //View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_konsul,parent, false)
  )

    override fun getItemCount(): Int {
       return mValues.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = mValues[position]
        holder.text_namaGuru.text = item.nama
        holder.text_mapel.text = item.keahlian

        holder.cardKonsul.setOnClickListener { view->
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(
                    "https://api.whatsapp.com/send/?phone=62" + item.nohp))
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               mContext.startActivity(i)
        }
    }


    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text_namaGuru: TextView = itemView.findViewById(R.id.text_namaGuru)
        var text_mapel: TextView = itemView.findViewById(R.id.text_mapel)
        var cardKonsul: CardView = itemView.findViewById(R.id.cardItemKonsul)
    }

}