package com.selfi.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.alarm.AlarmReceiver
import com.selfi.models.Todo
import com.selfi.models.response.ResponseDB
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TodoService
import com.selfi.ui.fragment.BSheetTodoEditFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

//import jdk.nashorn.internal.objects.NativeDate.getTime




class TodoRecyclerAdapter(private var mValues: List<Todo>, private var mContext: Context, private var update:()-> Unit) :
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
        var item = mValues[position]
        val pref = SharedPrefHelper.getInstance(mContext).getAccount().nis

        holder.txt_judul.text = item.kegiatan

        holder.cb_todo.setOnClickListener {

            if(holder.cb_todo.isChecked){

                val alarmReceiver = AlarmReceiver()

                if(alarmReceiver.isAlarmSet(mContext, alarmReceiver.TYPE_ONE_TIME)){
                    alarmReceiver.cancelAlarm(mContext, alarmReceiver.TYPE_ONE_TIME)
                }



                ServiceBuilder.buildService(TodoService::class.java, mContext).updateTodo(
                    pref, item.id,"completed").enqueue(object: Callback<ResponseDB> {
                    override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                        Toast.makeText(mContext, "Error : ${t.message}", Toast.LENGTH_SHORT)

                    }

                    override fun onResponse(call: Call<ResponseDB>, response: Response<ResponseDB>) {
                        update()

                    }

                })
            }
        }

        holder.card_todo.setOnClickListener {

            val data: Bundle = Bundle()
            val fragEdit = BSheetTodoEditFragment()
            data.putString("judulEdit", item.kegiatan)
            data.putInt("idEdit", item.id)
            fragEdit.arguments = data

            fragEdit.show(
                (mContext as FragmentActivity).supportFragmentManager,
                fragEdit.tag
            )

        }



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
        val cb_todo: CheckBox =  mView.findViewById(R.id.cb_todo)
        val card_todo: CardView = mView.findViewById(R.id.card_item_todo)
    }

}