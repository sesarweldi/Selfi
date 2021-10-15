package com.selfi.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.selfi.R
import kotlinx.android.synthetic.main.layout_bottom_sheet_todo.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class BSheetTodoFragment(): BottomSheetDialogFragment() {

    val cal = Calendar.getInstance()


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
        var edtInputTodo = view.edt_inputTodo
        var tanggalTodo = view.edt_inputTanggalTodo
        var jamTodo = view.edt_inputJamTodo


        //btn simpan
        btnSimpan.setOnClickListener {
            Toast.makeText(requireContext(),"Dipencet!", Toast.LENGTH_LONG).show()
        }


        dateDialog()
        //time picker
    }

    fun dateDialog(){
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateView()
            }
        }
        edt_inputTanggalTodo.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(activity!!,dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun updateDateView() {
        val formatTannggal= "dd/MM/yyyy"
        val sdf = SimpleDateFormat(formatTannggal, Locale.US)
        edt_inputTanggalTodo.setText(sdf.format(cal.time))
    }
}