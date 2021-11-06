package com.selfi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.Target

class TargetProfileRecyclerAdapter(private val mValues: List<Target>, private val mContext: Context):
    RecyclerView.Adapter<TargetProfileRecyclerAdapter.viewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = viewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_target_profile,parent, false)
    )

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: TargetProfileRecyclerAdapter.viewHolder, position: Int) {
        val item = mValues[position]
        holder.txtJudul.text = item.judulTarget
        holder.txtDeskripsi.text = item.deskripsiTarget
    }

    class viewHolder(mView: View): RecyclerView.ViewHolder(mView) {
        var txtJudul: TextView = itemView.findViewById(R.id.judul_profile_target)
        var txtDeskripsi: TextView = itemView.findViewById(R.id.deskripsi_target_profile)
    }
}