package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.selfi.R
import com.selfi.ui.fragment.BSheetTodoFragment
import kotlinx.android.synthetic.main.activity_todolist.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class TodolistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        toolbar_activity_title.setText("Todo List")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }


        floating_todo.setOnClickListener {

            val bottomSheetTodo = BSheetTodoFragment()
            bottomSheetTodo.show(supportFragmentManager,"TAG")

            /* val view: View = layoutInflater.inflate(R.layout.layout_bottom_sheet_todo,null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()*/
        }
    }
}
