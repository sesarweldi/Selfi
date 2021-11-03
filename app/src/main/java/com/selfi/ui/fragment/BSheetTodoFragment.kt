package com.selfi.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.selfi.R
import com.selfi.models.response.ResponseDB
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TodoService
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
        var btnSimpan = view.btn_simpanTodo

        pref_id = SharedPrefHelper(activity!!.applicationContext).getAccount().nis


        //btn simpan
//        btnSimpan.setOnClickListener {
//            Toast.makeText(requireContext(), "Dipencet!", Toast.LENGTH_LONG).show()
//        }

        //date picker
        dateDialog()

        //time picker
        timeDialog()

        btnSimpanClick()
    }

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


    fun btnSimpanClick() {
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
//                val datePart: LocalDate = LocalDate.parse(edt_inputTanggalTodo.text.toString())
//                val timePart: LocalTime = LocalTime.parse(edt_inputJamTodo.text.toString())
//                val dt: LocalDateTime = LocalDateTime.of(datePart, timePart)
//                val zoneId: ZoneId = ZoneId.of("Asia/Jakarta")
//                val zdt: ZonedDateTime =  ZonedDateTime.of(dt,zoneId)
//
//                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                val formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

                val tanggal: LocalDate = LocalDate.parse(edt_inputTanggalTodo.text.toString())
                val jam: LocalTime = LocalTime.parse(edt_inputJamTodo.text.toString())




                val service = ServiceBuilder.buildService(TodoService::class.java)
                service.addTodo(
                    id_todo,
                    pref_id,
                    edt_inputTodo.text.toString(),
                    onSetIsoDueDate(tanggal, jam)
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
                        }


                    }
                })

            }
        }
    }
//
    fun onSetIsoDueDate(date: LocalDate, time: LocalTime): String {
        val tmpDueDate = DateTime()
            .withDate(date)
            .withTime(time)

    Log.d("test",tmpDueDate.toString())
        return tmpDueDate.toString()
    }
}