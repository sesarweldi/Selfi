package com.selfi.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
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
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_todo.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_todo.view.*
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.min

class BSheetTodoFragment() : BottomSheetDialogFragment() {

    val cal = Calendar.getInstance()
    private var pref_id = 0
    private var id_todo = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        pref_id = SharedPrefHelper(activity!!.applicationContext).getAccount().nis

        /*
               dateDialog()
               timeDialog()
       */

        dateDialog()
        timeDialog()

        btnSimpanClick()
    }
/*
    fun dateDialog() {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateView()
            }
        }
        edt_inputTanggalTodo.setOnClickListener {
            DatePickerDialog(
                activity!!, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    private fun updateDateView() {
        val formatTannggal = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(formatTannggal, Locale.US)
        edt_inputTanggalTodo.setText(sdf.format(cal.time))
    }


    fun timeDialog() {
        edt_inputJamTodo.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)

                edt_inputJamTodo.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }

            TimePickerDialog(
                activity!!,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    */


    fun btnSimpanClick() {
        val alarmReceiver = AlarmReceiver()
        btn_simpanTodo.setOnClickListener {
            if (edt_inputTodo.text.toString().trim().isEmpty()) {
                edt_inputTodo.error = "Judul tidak boleh kosong"
                edt_inputTodo.requestFocus()
                return@setOnClickListener
            } else if (edt_inputTanggalTodo.text.toString().trim().isEmpty()) {
                edt_inputTanggalTodo.error = "Tanggal tidak boleh kosong"
                edt_inputTanggalTodo.requestFocus()
                return@setOnClickListener
            } else if (edt_inputJamTodo.text.toString().trim().isEmpty()) {
                edt_inputJamTodo.error = "Waktu tidak boleh kosong"
                edt_inputJamTodo.requestFocus()
                return@setOnClickListener
            } else {
                val datePart: java.time.LocalDate =
                    java.time.LocalDate.parse(edt_inputTanggalTodo.text.toString())
                val timePart: java.time.LocalTime =
                    java.time.LocalTime.parse(edt_inputJamTodo.text.toString())
                val dt: java.time.LocalDateTime = java.time.LocalDateTime.of(datePart, timePart)

                alarmReceiver.setAlarm(
                    activity!!,
                    alarmReceiver.TYPE_ONE_TIME,
                    edt_inputTanggalTodo.text.toString(),
                    edt_inputJamTodo.text.toString(),
                    edt_inputTodo.text.toString()
                )


                val service = ServiceBuilder.buildService(TodoService::class.java)
                service.addTodo(
                    id_todo,
                    pref_id,
                    edt_inputTodo.text.toString(),
                    dt
                ).enqueue(object : Callback<ResponseDB> {
                    override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                        Log.e("onFailure", t.message!!)
                    }

                    override fun onResponse(
                        call: Call<ResponseDB>,
                        response: Response<ResponseDB>
                    ) {
                        Toast.makeText(
                            activity!!.applicationContext,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

//                        Log.d("test", "msg : ${response.body()}" )


                        if (response.body()!!.success) {
                            this@BSheetTodoFragment.dismiss()
                            (activity as TodolistActivity).recyclerTodo()
                        }
                    }
                })

            }
        }
    }

    fun dateDialog(){
        val c: Calendar = Calendar.getInstance()
        var mYear: Int = c.get(Calendar.YEAR)
        var mMonth: Int = c.get(Calendar.MONTH)
        var mDay: Int = c.get(Calendar.DAY_OF_MONTH)


        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                edt_inputTanggalTodo.setText(String.format("%04d-%02d-%02d",year, month +1, dayOfMonth))
                mYear = year
                mMonth = month
                mDay = dayOfMonth

            }
        }

        edt_inputTanggalTodo.setOnClickListener {
            DatePickerDialog(activity!!, dateSetListener, mYear,mMonth, mDay).show()
        }
    }
    
    fun timeDialog(){
        val c: Calendar = Calendar.getInstance()
        var mHour: Int = c.get(Calendar.HOUR_OF_DAY)
        var mMinute: Int = c.get(Calendar.MINUTE)
        
        val timeSetListener = object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                edt_inputJamTodo.setText(String.format("%02d:%02d", hourOfDay, minute))
                mHour = hourOfDay
                mMinute = minute
            }
        }
        
        edt_inputJamTodo.setOnClickListener {
            TimePickerDialog(activity!!, timeSetListener, mHour, mMinute, true).show()
        }
    }
}