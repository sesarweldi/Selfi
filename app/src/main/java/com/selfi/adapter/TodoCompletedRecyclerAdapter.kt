package com.selfi.adapter

import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.models.Todo
import com.selfi.models.response.ResponseDB
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TodoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class TodoCompletedRecyclerAdapter(private val mValues: List<Todo>, private val mContext: Context,private var updateCompleted:()-> Unit):
    RecyclerView.Adapter<TodoCompletedRecyclerAdapter.viewHolder>() {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoCompletedRecyclerAdapter.viewHolder = viewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_todo_completed,parent, false))

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: TodoCompletedRecyclerAdapter.viewHolder, position: Int) {
        val item = mValues[position]
        holder.txt_judul.setPaintFlags(holder.txt_judul.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
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


        holder.card_todo.setOnClickListener {view ->
            var alert = AlertDialog.Builder(view.rootView.context)
            alert.setTitle("Hapus Tugas Selesai")
            alert.setMessage("Apakah anda ingin menghapus tugas ini dalam daftar Tugas Selesai?")
            alert.setPositiveButton("Ya", { dialog: DialogInterface?, which: Int ->

                val pref =
                    SharedPrefHelper.getInstance(mContext.applicationContext).getAccount().nis
                ServiceBuilder.buildService(TodoService:: class.java, mContext).deleteTodoCompleted(pref, item.id)
                    .enqueue(object : Callback<ResponseDB>{
                        override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                            Toast.makeText(
                                mContext.applicationContext,
                                "Error : ${t.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        override fun onResponse(
                            call: Call<ResponseDB>,
                            response: Response<ResponseDB>
                        ) {
                            if (response.isSuccessful) {
                                updateCompleted()
                            } else {
                                Toast.makeText(mContext.applicationContext, "Error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })


            })
            alert.setNegativeButton("Tidak", { dialog: DialogInterface?, which: Int -> })
            alert.show()
        }

    }

    class viewHolder(mView: View): RecyclerView.ViewHolder(mView) {
        val txt_tanggal: TextView = mView.findViewById(R.id.tv_tanggal_todo_selesai)
        val txt_jam: TextView = mView.findViewById(R.id.tv_jam_todo_selesai)
        val txt_bulan: TextView = mView.findViewById(R.id.tv_bulan_todo_selesai)
        val txt_hari: TextView = mView.findViewById(R.id.tv_hari_todo_selesai)
        val txt_judul: TextView = mView.findViewById(R.id.tv_judul_todo_selesai)

        val cb_todo: CheckBox = mView.findViewById(R.id.cb_todo_selesai)
        val card_todo: CardView = mView.findViewById(R.id.card_item_todo_completed)
    }
}