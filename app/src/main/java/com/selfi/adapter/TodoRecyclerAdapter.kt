package com.selfi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.Todo
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*


class TodoRecyclerAdapter(private var mValues: List<Todo>, private var mContext: Context) :
    RecyclerView.Adapter<TodoRecyclerAdapter.ListViewHolder>() {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoRecyclerAdapter.ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
    )

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: TodoRecyclerAdapter.ListViewHolder, position: Int) {
        val item = mValues[position]
        holder.txt_judul.text = item.kegiatan

        try {

            var outputFormat1 = SimpleDateFormat("EE")
            var date1: Date? = null
            date1 = inputFormat.parse(item.tanggal)
            val formattedDate1: String = outputFormat1.format(date1)
            holder.txt_hari.setText(formattedDate1)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            var outputFormat2 = SimpleDateFormat("MMM")
            var date2: Date? = null
            date2 = inputFormat.parse(item.tanggal)
            val formattedDate2: String = outputFormat2.format(date2)
            holder.txt_bulan.setText(formattedDate2)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        try {
            var outputFormat3 = SimpleDateFormat("dd-M-yyyy")
            var date3: Date? = null
            date3 = inputFormat.parse(item.tanggal)
            val formattedDate3: String = outputFormat3.format(date3)
            holder.txt_tanggal.setText(formattedDate3)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            var outputFormat4 = SimpleDateFormat("hh:mm aa")
            var date4: Date? = null
            date4 = inputFormat.parse(item.tanggal)
            val formattedDate4: String = outputFormat4.format(date4)
            holder.txt_jam.setText(formattedDate4)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    class ListViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val txt_tanggal: TextView = mView.findViewById(R.id.tv_tanggal_todo)
        val txt_jam: TextView = mView.findViewById(R.id.tv_jam_todo)
        val txt_bulan: TextView = mView.findViewById(R.id.tv_bulan_todo)
        val txt_hari: TextView = mView.findViewById(R.id.tv_hari_todo)
        val txt_judul: TextView = mView.findViewById(R.id.tv_judul_todo)
    }
}