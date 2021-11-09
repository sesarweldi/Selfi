package com.selfi.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.selfi.R
import com.selfi.adapter.TodoRecyclerAdapter
import com.selfi.models.Todo
import com.selfi.models.response.DataResponseModel
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TodoService
import com.selfi.ui.fragment.BSheetTodoFragment
import kotlinx.android.synthetic.main.activity_todolist.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        }

        tv_tugas_selesai.setOnClickListener {
            startActivity(Intent(this@TodolistActivity, TodolistCompletedActivity:: class.java))
        }

        recyclerTodo()

        swipe_todo.setOnRefreshListener {
            recyclerTodo()
        }
    }

     fun recyclerTodo(){
         val pref =  SharedPrefHelper.getInstance(applicationContext).getAccount().nis
         val service = ServiceBuilder.buildService(TodoService:: class.java, this@TodolistActivity).getTodoUncompleted(pref)
         service.enqueue(object: Callback<DataResponseModel<List<Todo>>>{
             override fun onFailure(call: Call<DataResponseModel<List<Todo>>>, t: Throwable) {
                 Toast.makeText(applicationContext, "Error : ${t.message}", Toast.LENGTH_SHORT)
                     .show()
             }

             override fun onResponse(
                 call: Call<DataResponseModel<List<Todo>>>,
                 response: Response<DataResponseModel<List<Todo>>>
             ) {
                 if(response.isSuccessful){
                     if (response.body()!!.data!!.isEmpty()) {
                         info_todo_kosong.visibility = View.VISIBLE
                         rv_todo.visibility = View.GONE
                     }

                     else{
                         info_todo_kosong.visibility = View.GONE
                         rv_todo.visibility = View.VISIBLE

                         rv_todo.setHasFixedSize(true)
                         rv_todo.layoutManager = LinearLayoutManager(this@TodolistActivity)
                         val adapter =
                             TodoRecyclerAdapter(response.body()!!.data!!, this@TodolistActivity ){ Toast.makeText(
                                 this@TodolistActivity, "Todo List selesai",
                                 Toast.LENGTH_SHORT
                             ).show()
                             recyclerTodo()
                             }
                         rv_todo.adapter = adapter
                     }
                     swipe_todo.isRefreshing = false
                 }

                 else{
                     Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                     swipe_todo.isRefreshing = false
                 }
             }
         })
     }
}
