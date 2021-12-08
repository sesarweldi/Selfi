package com.selfi.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.selfi.R
import com.selfi.alarm.AlarmReceiver
import com.selfi.models.response.ResponseDB
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TodoService
import com.selfi.ui.activity.TodolistActivity
import kotlinx.android.synthetic.main.fragment_bsheet_todo_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class BSheetTodoEditFragment() : BottomSheetDialogFragment() {


    var idTodo: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bsheet_todo_edit, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTodo()
        dateDialog()
        timeDialog()


    }

    fun editTodo() {
        val bundle = arguments
          idTodo = bundle!!.getInt("idEdit",0)
        et_judul_edit_todo.setText(bundle!!.getString("judulEdit"))
        val pref = SharedPrefHelper.getInstance(activity!!.applicationContext).getAccount().nis
        val alarmReceiver = AlarmReceiver()

     //   Log.d("test bs",arguments!!.getInt("idEdit",0).toString() )

        btn_simpan_edit_todo.setOnClickListener {
            if (et_judul_edit_todo.text.toString().trim().isEmpty()) {
                et_judul_edit_todo.error = "Judul tidak boleh kosong"
                et_judul_edit_todo.requestFocus()
                return@setOnClickListener
            } else if (et_tanggal_edit_todo.text.toString().trim().isEmpty()) {
                et_tanggal_edit_todo.error = "Tanggal tidak boleh kosong"
                et_tanggal_edit_todo.requestFocus()
                return@setOnClickListener
            } else if (et_jam_edit_todo.text.toString().trim().isEmpty()) {
                et_jam_edit_todo.error = "Jam tidak boleh kosong"
                et_jam_edit_todo.requestFocus()
                return@setOnClickListener
            } else {
                val datePart: java.time.LocalDate =
                    java.time.LocalDate.parse(et_tanggal_edit_todo.text.toString())
                val timePart: java.time.LocalTime =
                    java.time.LocalTime.parse(et_jam_edit_todo.text.toString())
                val dt: java.time.LocalDateTime = java.time.LocalDateTime.of(datePart, timePart)

                alarmReceiver.setAlarm(
                    activity!!,
                    alarmReceiver.TYPE_ONE_TIME,
                    et_tanggal_edit_todo.text.toString(),
                    et_jam_edit_todo.text.toString(),
                    et_judul_edit_todo.text.toString()
                )

                ServiceBuilder.buildService(TodoService::class.java, activity!!)
                    .editTodo(pref, idTodo, "on_progress", et_judul_edit_todo.text.toString(), dt).enqueue(object :
                        Callback<ResponseDB> {
                        override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                            Toast.makeText(
                                activity!!,
                                "Error : ${t.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        override fun onResponse(
                            call: Call<ResponseDB>,
                            response: Response<ResponseDB>
                        ) {

                            Toast.makeText(
                                activity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            if (response.body()!!.success) {
                                this@BSheetTodoEditFragment.dismiss()
                                (activity as TodolistActivity).recyclerTodo()
                            }
                        }
                    })

            }
        }
    }

    fun dateDialog() {
        val c: Calendar = Calendar.getInstance()
        var mYear: Int = c.get(Calendar.YEAR)
        var mMonth: Int = c.get(Calendar.MONTH)
        var mDay: Int = c.get(Calendar.DAY_OF_MONTH)


        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                et_tanggal_edit_todo.setText(
                    String.format(
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        dayOfMonth
                    )
                )
                mYear = year
                mMonth = month
                mDay = dayOfMonth

            }
        }

        et_tanggal_edit_todo.setOnClickListener {
            DatePickerDialog(activity!!, dateSetListener, mYear, mMonth, mDay).show()
        }

        img_edt_tanggal.setOnClickListener {
            DatePickerDialog(activity!!, dateSetListener, mYear, mMonth, mDay).show()
        }
    }


    fun timeDialog() {
        val c: Calendar = Calendar.getInstance()
        var mHour: Int = c.get(Calendar.HOUR_OF_DAY)
        var mMinute: Int = c.get(Calendar.MINUTE)

        val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                et_jam_edit_todo.setText(String.format("%02d:%02d", hourOfDay, minute))
                mHour = hourOfDay
                mMinute = minute
            }
        }


        et_jam_edit_todo.setOnClickListener {
            TimePickerDialog(
                activity!!,
                timeSetListener,
                mHour,
                mMinute,
                true
            ).show()
        }

        img_edt_jam.setOnClickListener {
            TimePickerDialog(
                activity!!,
                timeSetListener,
                mHour,
                mMinute,
                true
            ).show()
        }
    }
}
